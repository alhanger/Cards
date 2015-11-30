package com.theironyard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits = hand.stream()
                                        .map(card -> {
                                            return card.suit;
                                        })
                                        .collect(Collectors.toCollection(HashSet::new));
        return suits.size() == 1;
    }

    static boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Integer> ranks = hand.stream()
                                        .map(card -> {
                                            return card.rank.ordinal();
                                        })
                                        .sorted()
                                        .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> rankCheck = new ArrayList<>();
        int i = ranks.get(0);
        for (int r = 0; r < ranks.size(); r++) {
            rankCheck.add(i + r);
        }

        return ranks.equals(rankCheck);
    }

    static boolean isStraightFlush(ArrayList<Card> hand) {
        HashSet<Card> newHand = new HashSet<>(hand);

        return isFlush(newHand) && isStraight(hand);
    }

    static boolean isFour(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = hand.stream()
                                        .map(card -> {
                                            return card.rank;
                                        })
                                        .collect(Collectors.toCollection(HashSet::new));

        return ranks.size() == 1;
    }

//    static boolean isThree(HashSet<Card> hand) {
//        HashSet<Card.Rank> ranks = hand.stream()
//                                        .map(card -> {
//                                            return card.rank;
//                                        })
//                                        .collect(Collectors.toCollection(HashSet::new));
//
//
//    }

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();

        Card aceOfSpades = new Card(Card.Suit.SPADE, Card.Rank.ACE);
        Card twoOfSpades = new Card(Card.Suit.SPADE, Card.Rank.TWO);
        Card threeOfSpades = new Card(Card.Suit.SPADE, Card.Rank.THREE);
        Card fourOfSpades = new Card(Card.Suit.SPADE, Card.Rank.SIX);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(aceOfSpades);
        hand.add(twoOfSpades);
        hand.add(threeOfSpades);
        hand.add(fourOfSpades);
        System.out.println(isStraight(hand));
        HashSet<Card> deck = createDeck();
        //System.out.println(deck.size());
        //System.out.println(deck.contains(aceOfSpades));
        HashSet<HashSet<Card>> hands = createHands(deck);
        hands = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet::new));
        System.out.println(hands.size());

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Elapsed Time: %d", endTime - beginTime));
    }
}
