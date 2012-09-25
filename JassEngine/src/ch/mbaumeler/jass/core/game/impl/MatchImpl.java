package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.JassRules;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.ScoreRules;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.core.game.wys.Wys;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;
import ch.mbaumeler.jass.core.game.wys.WysStore;

public class MatchImpl implements Match {

	/**
	 * Number of Cards in a deck.
	 */
	private static final int CARDS = 36;

	/**
	 * Number of players in a jass game.
	 */
	private static final int PLAYERS = 4;

	/**
	 * Current choosen trumpf.
	 */
	private Ansage ansage;

	/**
	 * Map between players and the cards in their hands.
	 */
	private final List<PlayedCard> cards;

	/**
	 * The players of the match.
	 */
	private final PlayerTokenRepository playerTokenRepository;

	/**
	 * Stack of cards which where played.
	 */
	private final List<PlayedCard> playedCards = new ArrayList<PlayedCard>();

	/**
	 * Util to get the score.
	 */
	private final ScoreUtil scoreUtil;

	private final JassRules jassRules;

	/**
	 * Number between 0 and 3. If one match was played, the offset 1.
	 */
	private final int startingPlayerOffset;

	private WysStore wysStore;

	private boolean geschoben;

	public MatchImpl(PlayerTokenRepository playerRepository, PlayerToken startingPlayer, ScoreUtil scoreUtil,
			JassRules jassRules, List<PlayedCard> shuffledDeck, ScoreRules scoreRules, WysRules wysRule,
			WysScoreRule wysScoreRule) {
		this.playerTokenRepository = playerRepository;
		List<PlayerToken> allPlayers = playerTokenRepository.getAll();
		this.startingPlayerOffset = allPlayers.indexOf(startingPlayer);
		this.scoreUtil = scoreUtil;
		this.jassRules = jassRules;
		this.wysStore = new WysStore(wysRule, wysScoreRule, this);
		this.cards = shuffledDeck;
	}

	@Override
	public List<PlayedCard> getCards(PlayerToken player) {

		ArrayList<PlayedCard> cardsFromPlayer = new ArrayList<PlayedCard>();
		for (PlayedCard playedCard : cards) {
			if (playedCard.getPlayer() == player) {
				cardsFromPlayer.add(playedCard);
			}
		}
		return cardsFromPlayer;
	}

	@Override
	public List<PlayedCard> getCardsOnTable() {

		return playedCards.subList(getRoundsCompleted() * PLAYERS, playedCards.size());
	}

	private boolean isNewRoundStarted() {
		return playedCards.size() % PLAYERS == 0;
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

		int index = playedCards.size();
		boolean firstRound = getRoundsCompleted() == 0;
		List<PlayerToken> allPlayers = playerTokenRepository.getAll();
		index += (firstRound) ? startingPlayerOffset : allPlayers.indexOf(getWinnerlastRound());
		PlayerToken activePlayer = allPlayers.get(index % 4);
		if (ansage == null && isGeschoben()) {
			return playerTokenRepository.getTeamPlayer(activePlayer);
		}
		return activePlayer;
	}

	private PlayerToken getWinnerlastRound() {
		List<PlayedCard> cardsFromRound = getCardsFromRound(getRoundsCompleted() - 1);
		return scoreUtil.getWinnerCard(cardsFromRound, ansage).getPlayer();
	}

	@Override
	public boolean isCardPlayable(PlayedCard card) {
		return jassRules.isCardPlayable(card, getCards(getActivePlayer()), getCardsOnTable(), ansage,
				isNewRoundStarted());
	}

	@Override
	public List<PlayedCard> getCardsFromRound(int i) {
		return playedCards.subList(i * PLAYERS, i * PLAYERS + PLAYERS);
	}

	@Override
	public void playCard(PlayedCard card) {
		if (card == null || !isCardPlayable(card)) {
			throw new IllegalArgumentException("Card is not playable: " + card);
		}
		cards.remove(card);
		playedCards.add(card);
	}

	@Override
	public int getRoundsCompleted() {
		return playedCards.size() / PLAYERS;
	}

	@Override
	public boolean isComplete() {
		return playedCards.size() == CARDS;
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
}
