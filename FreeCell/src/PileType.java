/**
 * Type for the three types of piles in a game of Freecell.
 * Open: This pile can hold only one card. It is like a buffer to temporarily
 * hold cards.
 * Cascade: This is a pile of face-up cards. A build within a cascade
 * pile is a subset of cards that has monotonically decreasing values and suits
 * of alternating colors
 * Foundation: Initially empty, there are 4 foundation
 * piles, one for each suit. Each foundation pile collects cards in increasing
 * order of value for one suit (Ace being the lowest).
 * The goal of the game is to fill up all the foundation piles
 */
public enum PileType {
  OPEN, CASCADE, FOUNDATION
}
