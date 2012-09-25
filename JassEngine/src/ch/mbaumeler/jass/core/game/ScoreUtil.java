package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardValue.JACK;
import static ch.mbaumeler.jass.core.card.CardValue.NINE;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.wys.Wys;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;
import ch.mbaumeler.jass.core.game.wys.WysStore;

public class ScoreUtil {

	private static final int SCORE_FOR_A_MATCH = 100;

	private static final int LAST_ROUND_WINNER_POINTS = 5;

	@Inject
	PlayerTokenRepository playerTokenRepository;

	@Inject
	ScoreRules scoreRules;

	@Inject
	WysRules wysRules;

	@Inject
	WysScoreRule wysScoreRule;

	public Score calculateScore(Match match, WysStore wysStore) {
		Score score = new Score(playerTokenRepository);

		addScoreForAnsage(match, score);

		addScoreForMatch(match, score);
		addScoreForStoeck(wysStore, score);
		addScoreForWys(wysStore, score, match.getAnsage());
		addScoreForLastRoundWinner(match, score);

		return score;
	}

	private void addScoreForMatch(Match match, Score score) {
		PlayerToken team1player = playerTokenRepository.getTeam1().get(0);
		PlayerToken team2Player = playerTokenRepository.getTeam2().get(0);

		if (score.getPlayerScore(team1player) == 152) {
			score.addScore(team1player, SCORE_FOR_A_MATCH);
		} else if (score.getPlayerScore(team2Player) == 152) {
			score.addScore(team2Player, SCORE_FOR_A_MATCH);
		}

	}

	private void addScoreForWys(WysStore wysStore, Score score, Ansage ansage) {
		PlayerToken bestWysPlayer = wysStore.getBestWys(ansage);
		addScore(bestWysPlayer, wysStore, score);
		PlayerToken teamPlayer = playerTokenRepository.getTeamPlayer(bestWysPlayer);
		addScore(teamPlayer, wysStore, score);
	}

	public PlayedCard getWinnerCard(List<PlayedCard> cardsOnTable, Ansage ansage) {

		PlayedCard currentWinner = cardsOnTable.get(0);

		for (PlayedCard playedCard : cardsOnTable) {

			switch (ansage.getSpielModi()) {
			case TRUMPF:
				boolean isSameSuiteOrTrumpf = (isSameSuit(playedCard, currentWinner) || isTrumpf(ansage, playedCard));
				if (isSameSuiteOrTrumpf && isHigher(ansage, playedCard, currentWinner)) {
					currentWinner = playedCard;
				}
				break;

			case UNDEUFE:
				if (isSameSuit(playedCard, currentWinner) && !isHigher(ansage, playedCard, currentWinner)) {
					currentWinner = playedCard;
				}
				break;

			case OBENABE:
				if (isSameSuit(playedCard, currentWinner) && isHigher(ansage, playedCard, currentWinner)) {
					currentWinner = playedCard;
				}
				break;
			}
		}
		return currentWinner;
	}

	private boolean isHigher(Ansage ansage, PlayedCard playedCard, PlayedCard currentWinner) {
		if (isTrumpf(ansage, playedCard) && !isTrumpf(ansage, currentWinner)) {
			return true;
		}
		return getValue(ansage, playedCard) > getValue(ansage, currentWinner);
	}

	private int getValue(Ansage ansage, PlayedCard card) {
		if (ansage.is(card.getSuit()) && card.getValue() == JACK) {
			return 20;
		}

		if (ansage.is(card.getSuit()) && card.getValue() == NINE) {
			return 14;
		}
		return card.getValue().ordinal();
	}

	private boolean isTrumpf(Ansage ansage, PlayedCard playedCard) {
		return ansage.is(playedCard.getSuit());
	}

	private boolean isSameSuit(PlayedCard playedCard, PlayedCard playedCard2) {
		return playedCard.getSuit().equals(playedCard2.getSuit());
	}

	private void addScore(PlayerToken playerToken, WysStore wysStore, Score score) {
		Set<Wys> set = wysStore.getWys(playerToken);
		if (set != null) {
			for (Wys wys2 : set) {
				score.addScore(playerToken, wysScoreRule.getScoreFore(wys2));
			}
		}
	}

	private void addScoreForStoeck(WysStore wysStore, Score score) {
		if (wysStore.getStoeckFromPlayer() != null) {
			score.addScore(wysStore.getStoeckFromPlayer(), wysScoreRule.getScoreForStoeck());
		}
	}

	private void addScoreForAnsage(Match match, Score score) {
		Ansage ansage = match.getAnsage();
		for (int i = 0; i < match.getRoundsCompleted(); i++) {
			List<PlayedCard> cardsFromRound = match.getCardsFromRound(i);
			PlayerToken winner = getWinnerCard(cardsFromRound, ansage).getPlayer();
			int winnerScore = getScore(cardsFromRound, ansage);
			score.addScore(winner, winnerScore);
		}
	}

	private void addScoreForLastRoundWinner(Match match, Score score) {
		if (match.isComplete()) {
			score.addScore(getWinnerlastRound(match), LAST_ROUND_WINNER_POINTS);
		}
	}

	private int getScore(List<PlayedCard> cardsFromRound, Ansage trumpf) {
		int totalScore = 0;
		for (PlayedCard playedCard : cardsFromRound) {
			totalScore += scoreRules.getScore(playedCard, trumpf);
		}
		return totalScore;
	}

	private PlayerToken getWinnerlastRound(Match match) {
		List<PlayedCard> cardsFromRound = match.getCardsFromRound(match.getRoundsCompleted() - 1);
		return getWinnerCard(cardsFromRound, match.getAnsage()).getPlayer();
	}
}
