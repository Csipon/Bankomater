package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class CurrencyManipulatorFactory
{
    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory(){

    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        map.putIfAbsent(currencyCode, new CurrencyManipulator(currencyCode));
        return map.get(currencyCode);
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){

        return map.values();
    }
}
