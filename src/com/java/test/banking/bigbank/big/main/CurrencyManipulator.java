package main;

import exception.NotEnoughMoneyException;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator
{
    private String currencyCode;

    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    private Map<Integer, Integer> denominations = new TreeMap<Integer, Integer>(new Comparator<Integer>()
    {
        @Override
        public int compare(Integer o1, Integer o2)
        {
            return (o1 > o2) ? -1 : ((o1 == o2) ? 0 : 1);
        }
    });

    public CurrencyManipulator(String currencyCode){
        this.currencyCode = currencyCode;
    }




    public void addAmount(int denomination, int count){
        if (denominations.containsKey(denomination)){
            denominations.put(denomination, denominations.get(denomination) + count);
        }else
        {
            denominations.put(denomination, count);
        }
    }




    public int getTotalAmount(){
        int total = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet()){
            total+= pair.getValue() * pair.getKey();
        }
        return total;
    }



    public String getCurrencyCode()
    {
        return currencyCode;
    }



    public boolean hasMoney(){
          return denominations.size() > 0;
    }



    public boolean isAmountAvailable(int expectedAmount){
        return expectedAmount <= getTotalAmount();
    }



    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {
        Map<Integer, Integer> map = new TreeMap<>(denominations);

        int amount = expectedAmount;

        Map<Integer, Integer> result = new TreeMap<>(new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return -o1.compareTo(o2);
            }
        });

        Iterator<Map.Entry<Integer, Integer>> iterator = denominations.entrySet().iterator();

        while (iterator.hasNext()){

            Map.Entry<Integer, Integer> x = iterator.next();

            int nominal = x.getKey();
            int count = x.getValue();

            int countBanknot = 0;
            while ((count > 0) && (amount - nominal) >= 0){
                amount -= nominal;
                --count;
                if (count == 0){
                    x.setValue(0);
                }else
                {
                    x.setValue(count);
                }
                countBanknot++;
            }

            if (countBanknot > 0){
                result.put(nominal, countBanknot);
            }
        }

        if (amount == 0 && getTotalAmount() == 0){
            denominations.clear();
            denominations.putAll(map);
            for (Map.Entry<Integer, Integer> d : denominations.entrySet()){
                denominations.put(d.getKey(), 0);
            }
        }

        if (amount > 0 && amount < getTotalAmount()){

            denominations.clear();
            denominations.putAll(map);
            ConsoleHelper.writeMessage("These notes is impossible to give the requested amount");

            throw new NotEnoughMoneyException();
        }
        return result;
    }
}
