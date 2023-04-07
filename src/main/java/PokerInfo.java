import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PokerInfo implements Serializable {

    public ArrayList<Integer> cards;

    public int money;

    public int moneyWon;

    public int ante;

    public int pairPlusAmount;

    public boolean isPairPlus;

    private ArrayList<Integer> userCards;

    public ArrayList<Integer> dealerCards;

    int gamePhase;
    public PokerInfo(boolean isPairPlus, int ante, int pairPlusAmount) {
        cards = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            cards.add(i);
        }
        this.isPairPlus = isPairPlus;
        this.ante = ante;
        this.pairPlusAmount = pairPlusAmount;
        this.gamePhase = 1;
    }

    public void generateRandomCards(ArrayList<Integer> arrayList) {
        arrayList.clear();
        Random nums = new Random();
        int cards = 0;
        while (cards < 3) {
            int rand = nums.nextInt(52) + 1;
            if (!arrayList.contains(rand)) {
                cards++;
                arrayList.add(rand);
            }
        }
    }








}
