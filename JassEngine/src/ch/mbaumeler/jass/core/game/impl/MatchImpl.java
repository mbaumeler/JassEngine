package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
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
	 * Number of cards a player holds in his hands.
	 */
	private static final int CARDS_PER_PLAYER = CARDS / PLAYERS;

	/**
	 * Current choosen trumpf.
	 */
	private Ansage ansage;

	/**
	 * Map between players and the cards in their hands.
	 */
	private final Map<PlayerToken, List<Card>> cards = new HashMap<PlayerToken, List<Card>>();

	/**
	 * The players of the match.
	 */
	private final List<PlayerToken> players;

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

	public MatchImpl(PlayerTokenRepository playerRepository, PlayerToken startingPlayer, ScoreUtil scoreUtil,
			JassRules jassRules, List<Card> shuffledDeck, ScoreRules scoreRules, WysRules wysRule,
			WysScoreRule wysScoreRule) {
		this.players = playerRepository.getAll();
		this.startingPlayerOffset = players.indexOf(startingPlayer);
		this.scoreUtil = scoreUtil;
		this.jassRules = jassRules;
		this.wysStore = new WysStore(wysRule, wysScoreRule, this);
		for (int i = 0; i < PLAYERS; i++) {
			List<Card> subList = shuffledDeck.subList(i * CARDS_PER_PLAYER, i * CARDS_PER_PLAYER + CARDS_PER_PLAYER);
			cards.put(players.get(i), new ArrayList<Card>(subList));
		}
	}

	@Override
	public List<Card> getCards(PlayerToken player) {
		return cards.get(player);
	}

	@Override
	public List<PlayedCard> getCardsOnTable() {

		if (isNewRoundStarted() && getRoundsCompleted() > 0) {
			int fromIndex = (getRoundsCompleted() - 1) * PLAYERS;
			return playedCards.subList(fromIndex, fromIndex + PLAYERS);
		}
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
		index += (firstRound) ? startingPlayerOffset : players.indexOf(getWinnerlastRound());
		return players.get(index % 4);
	}

	private PlayerToken getWinnerlastRound() {
		List<PlayedCard> cardsFromRound = getCardsFromRound(getRoundsCompleted() - 1);
		return scoreUtil.getWinnerCard(cardsFromRound, ansage).getPlayer();
	}

	private CardSuit getCurrentSuit() {
		List<PlayedCard> cardsOnTable = getCardsOnTable();
		return cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getCard().getSuit();
	}

	@Override
	public boolean isCardPlayable(Card card) {
		return jassRules.isCardPlayable(card, getCards(getActivePlayer()), getCurrentSuit(), ansage,
				isNewRoundStarted());
	}

	@Override
	public List<PlayedCard> getCardsFromRound(int i) {
		return playedCards.subList(i * PLAYERS, i * PLAYERS + PLAYERS);
	}

	@Override
	public void playCard(Card card) {
		if (card == null || !isCardPlayable(card)) {
			throw new IllegalArgumentException("Card is not playable: " + card);
		}
		PlayerToken activePlayer = getActivePlayer();
		getCards(activePlayer).remove(card);
		playedCards.add(new PlayedCard(card, activePlayer));
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
}
