package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.MatchState;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.JassRules;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.ScoreRules;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.core.game.wys.Wys;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;
import ch.mbaumeler.jass.core.game.wys.WysStore;

/* REVIEW NEEDED */public class MatchImpl implements Match {

	/**
	 * Current choosen trumpf.
	 */
	private Ansage ansage;

	/**
	 * Map between players and the cards in their hands.
	 */
	private final List<Card> cards;

	/**
	 * Stack of cards which where played.
	 */
	private final List<Round> rounds;

	/**
	 * Util to get the score.
	 */
	private final ScoreUtil scoreUtil;

	private final JassRules jassRules;

	private final PlayerToken startingPlayer;

	private final WysStore wysStore;

	private boolean geschoben;

	public MatchImpl(PlayerToken startingPlayer, ScoreUtil scoreUtil, JassRules jassRules, List<Card> shuffledDeck,
			ScoreRules scoreRules, WysRules wysRule, WysScoreRule wysScoreRule) {
		this.startingPlayer = startingPlayer;
		this.scoreUtil = scoreUtil;
		this.jassRules = jassRules;
		this.wysStore = new WysStore(wysRule, wysScoreRule, this);
		this.cards = shuffledDeck;
		this.rounds = new ArrayList<Round>();
		this.rounds.add(new Round());
	}

	public MatchImpl(MatchState matchState, ScoreUtil scoreUtil, JassRules jassRules, ScoreRules scoreRules,
			WysRules wysRule, WysScoreRule wysScoreRule) {
		this.scoreUtil = scoreUtil;
		this.jassRules = jassRules;
		this.startingPlayer = matchState.getStartingPlayer();
		this.cards = matchState.getCards();
		this.geschoben = matchState.isGeschoben();
		this.rounds = matchState.getPlayedCards();
		this.wysStore = new WysStore(wysRule, wysScoreRule, this, matchState.getWysMap(), matchState.getStoeckPlayer());
		this.ansage = matchState.getAnsage();
	}

	@Override
	public List<Card> getCards(PlayerToken player) {

		ArrayList<Card> cardsFromPlayer = new ArrayList<Card>();
		for (Card playedCard : cards) {
			if (playedCard.getPlayer() == player) {
				cardsFromPlayer.add(playedCard);
			}
		}
		return cardsFromPlayer;
	}

	@Override
	public List<Card> getCardsOnTable() {
		return getCurrentRound().getCards();
	}

	private Round getCurrentRound() {
		return rounds.get(rounds.size() - 1);
	}

	private Round getLastRound() {
		return rounds.get(rounds.size() - 2);
	}

	private boolean isNewRoundStarted() {
		return getCurrentRound().getCards().isEmpty();
	}

	@Override
	public Ansage getAnsage() {
		return ansage;
	}

	@Override
	public void setAnsage(Ansage ansage) {
		if (this.ansage != null) {
			throw new IllegalArgumentException("Ansage already set to " + this.ansage);
		}
		this.ansage = ansage;
	}

	@Override
	public PlayerToken getActivePlayer() {
		List<PlayerToken> allPlayers = Arrays.asList(PlayerToken.values());
		Round currentRound = getCurrentRound();

		if (getRoundsCompleted() == 0 && currentRound.isEmpty()) {
			PlayerToken activePlayer = startingPlayer;
			if (ansage == null && isGeschoben()) {
				return PlayerToken.getTeamPlayer(activePlayer);
			}
			return activePlayer;
		} else {
			if (currentRound.isEmpty()) {
				return scoreUtil.getWinnerCard(getLastRound().getCards(), ansage).getPlayer();
			} else {
				PlayerToken player = currentRound.getLastPlayedCard().getPlayer();
				int indexOf = allPlayers.indexOf(player);
				return allPlayers.get((indexOf + 1) % 4);
			}
		}
	}

	@Override
	public boolean isCardPlayable(Card card) {
		return !getCurrentRound().isComplete()
				&& jassRules.isCardPlayable(card, getCards(getActivePlayer()), getCardsOnTable(), ansage,
						isNewRoundStarted());
	}

	@Override
	public List<Card> getCardsFromRound(int i) {
		return rounds.get(i).getCards();
	}

	@Override
	public void playCard(Card card) {
		if (card == null || !isCardPlayable(card)) {
			throw new IllegalArgumentException("Card is not playable: " + card);
		}
		cards.remove(card);
		getCurrentRound().addCard(card);
	}

	@Override
	public int getRoundsCompleted() {
		return getCurrentRound().isComplete() ? rounds.size() : rounds.size() - 1;
	}

	@Override
	public boolean isComplete() {
		return rounds.size() == 9 && getCurrentRound().isComplete();
	}

	@Override
	public Score getScore() {
		return scoreUtil.calculateScore(this, wysStore);
	}

	@Override
	public void wys(Set<Wys> wysSet) {
		wysStore.addWys(wysSet);
	}

	@Override
	public void schiebe() {
		if (ansage != null) {
			throw new IllegalStateException("Ansage already set");
		}
		geschoben = true;
	}

	@Override
	public boolean isGeschoben() {
		return geschoben;
	}

	@Override
	public MatchState createMatchState() {
		return new MatchState(ansage, cards, rounds, startingPlayer, geschoben, wysStore.getWysMap(),
				wysStore.getStoeckFromPlayer());
	}

	@Override
	public void collectCards() {

		if (!getCurrentRound().isComplete()) {
			throw new IllegalArgumentException("Current round is not complete");
		}

		if (!isComplete()) {
			rounds.add(new Round());
		}
	}
}
