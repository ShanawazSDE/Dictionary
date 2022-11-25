package com.example.dictionary;

import java.util.HashMap;

public class DictionaryLogic {
    HashMap<String,String> dictionary;
    DatabaseHelper databaseHelper;
    public DictionaryLogic(){
        dictionary = new HashMap<>();
       // addListOfWords();
      databaseHelper = new DatabaseHelper();
    }

    public boolean addWord(String word, String meaning){
        dictionary.put(word,meaning);
        return true;
    }

    public String searchWord(String word){
        if(dictionary.containsKey(word)){
            return dictionary.get(word);
        }
        return "word not found";
    }

    public String searchMeaning(String word){
        String meaning;
       meaning =  databaseHelper.search(word+" ");
       if(meaning.isBlank()) {
           return "word not found";
       }
       return meaning;
    }

    public void addListOfWords(){
        addWord("alligator","another version of crocs");
        addWord("conspiracy","chor kama");
        addWord("alibi","surity");
        addWord("teetotaler","un alcoholic");
        addWord("distinct","unique");
        addWord("babu","shona");

    }
}
