package hotstone.standard;

import hotstone.framework.*;
import hotstone.framework.Strategy.*;
import hotstone.standard.CardEffects.NoEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpyMutableGame implements MutableGame {
    private Map<Player, List<MutableCard>> hand = new HashMap<>();
    private Map<Player, MutableHero> heroes = new HashMap<>();
    private Map<Player, List<MutableCard>> field = new HashMap<>();
    private Map<Player, List<MutableCard>> deck = new HashMap<>();
    private ManaStrategy ms;
    private DeckStrategy ds;
    private HeroStrategy hs;
    private WinnerStrategy ws;

    public String lastCall = "";


    public SpyMutableGame(AbstractFactory factory) {
        ms = factory.createManaStrategy();
        ds = factory.createDeckStrategy();
        hs = factory.createHeroStrategy();
        ws = factory.createWinnerStrategy();
        field.put(Player.FINDUS, new ArrayList<>());
        field.put(Player.PEDDERSEN, new ArrayList<>());
        heroes.put(Player.FINDUS, new StdHero(Player.FINDUS, ms, hs));
        heroes.put(Player.PEDDERSEN, new StdHero(Player.PEDDERSEN, ms, hs));
        hand.put(Player.FINDUS, new ArrayList<>());
        hand.put(Player.PEDDERSEN, new ArrayList<>());
        dealCards();

    }
    private void dealCards(){
//        Initialize the decks for both players

        deck.put(Player.FINDUS, new ArrayList<>());
        deck.put(Player.PEDDERSEN, new ArrayList<>());

        for(String name : ds.getDeck()){
            deck.get(Player.FINDUS).add(new StdCard(name, Player.FINDUS, ds, ds.getEffect(name)));
        }

        for(String name : ds.getDeck()){
            deck.get(Player.PEDDERSEN).add(new StdCard(name, Player.PEDDERSEN, ds, ds.getEffect(name)));
        }

//      shuffle the cards as needed
        deck.put(Player.FINDUS, ds.arrangeDeck(deck.get(Player.FINDUS)));
        deck.put(Player.PEDDERSEN, ds.arrangeDeck(deck.get(Player.PEDDERSEN)));


//    deal 3 cards to both players
        for(int i = 0; i < 3; i++){
            drawCard(Player.FINDUS);
            drawCard(Player.PEDDERSEN);
        }

    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return field.get(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return field.get(who).size();
    }

    @Override
    public int getHandSize(Player who) {
        return hand.get(who).size();
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return hand.get(who);
    }

    @Override
    public MutableCard getMutableCardInHand(Player who, int index) {
        return hand.get(who).get(index);
    }

    @Override
    public MutableHero getMutableHero(Player who){
        return heroes.get(who);
    }

    @Override
    public MutableCard getMutableCardInField(Player who, int index){
        return field.get(who).get(index);
    }

    @Override
    public void removeCardOnField(Player who, MutableCard target) {
        lastCall = "removeCardOnField";
        field.get(who).remove(target);
    }

    @Override
    public void addCardToField(Player who, MutableCard card){
        lastCall = "addCardToField";
        field.get(who).addLast(card);
    }

    @Override
    public void drawCard(Player who){
        if (!deck.get(who).isEmpty()) {
            lastCall = "drawCard";
            MutableCard drawnCard =  deck.get(who).getFirst();
            deck.get(who).removeFirst();
            hand.get(who).addFirst(drawnCard);
        }
    }

    @Override
    public void addSovsToField() {
        Player pl = Player.FINDUS;
        addCardToField(pl, new StdCard(GameConstants.SOVS_CARD, pl, ds, new NoEffect()));
        lastCall = "addSovsToField";
    }

    @Override
    public void decreaseHeroHealth(Player who, int value) {
        getMutableHero(who).decreaseHealthBy(value);
        lastCall = "decreaseHeroHealth";
    }

    @Override
    public void increaseAttackofCardInField(Player who, int index, int value) {
        getMutableCardInField(who, index).increaseAttack(value);
        lastCall = "increaseAttackOfCardInField";
    }

    @Override
    public void decreaseHeroMana(Player who, int value) {
        getMutableHero(who).decreaseManaBy(value);
        lastCall = "decreaseHeroMana";
    }

    @Override
    public void addCardToHand(Player who, MutableCard card) {
        hand.get(who).addLast(card);
        lastCall = "addCardToHand";
    }

    @Override
    public MutableCard createMutableCard(Player owner, String name, DeckStrategy ds, CardEffectStrategy es) {
        lastCall = "createMutableCard";
        return new StdCard(name, owner, ds, es);
    }
}
