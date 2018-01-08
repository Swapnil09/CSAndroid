package com.example.android.wordit;

/**
 * Created by omkartuppe on 01/01/18.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class WordDictionary {
    private HashMap<String,HashSet<String>> lettersToWorld;
    public HashSet<String> result;
    private Random random;
    public WordDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        lettersToWorld = new HashMap<String,HashSet<String>>();
        result = new HashSet<String>();
        random = new Random();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            String key = sortLetters(word);
            if(word.length() >= 4 && word.length() <= 5){
                if(!lettersToWorld.containsKey(key)) {
                    lettersToWorld.put(key, new HashSet<String>());
                    lettersToWorld.get(key).add(word);
                }
                else{
                    lettersToWorld.get(key).add(word);
                }
            }
        }
    }
    public boolean isWord(String word) {
        String sortedOrder = sortLetters(word);
        if(lettersToWorld.containsKey(sortedOrder)){
            if(lettersToWorld.get(sortedOrder).contains(word)){
                return true;
            }
        }
        return false;
    }

    public String getNewLettersSequence(){
        String word = new String();
        ArrayList<String> sequences = new ArrayList(lettersToWorld.keySet());
        int index = random.nextInt(sequences.size());
        word = sequences.get(index);
        Log.i("word",word);
        int first,second;
        first = random.nextInt(25) + 97;
        second = random.nextInt(25) + 97;
        index = random.nextInt(25) + 97;
        char []temp = new char[5];
        if(word.length() == 4){
            word = word + Character.toString((char)first) + Character.toString((char)second) + Character.toString((char)index) ;
            word = sortLetters(word);
            getResultantWords(word.toCharArray(),temp, 0, 6, 0, 4);
            getResultantWords(word.toCharArray(),temp, 0, 6, 0, 5);
        }
        else {
            word = word + Character.toString((char) first) + Character.toString((char) second);
            word = sortLetters(word);
            getResultantWords(word.toCharArray(),temp, 0, 6, 0, 4);
            getResultantWords(word.toCharArray(),temp, 0, 6, 0, 5);
        }
        Log.i("word",word);
        return word;
    }
    public String sortLetters(String word){
        char [] letters = word.toCharArray();
        Arrays.sort(letters);
        String alphabeticalOrder = new String(letters);
        return alphabeticalOrder;
    }
    public void getResultantWords(char[] arr, char [] tempResultantWord, int start, int end, int index, int wordLength)
    {
        String resultantWord = new String();
        if (index == wordLength)
        {
            resultantWord = "";
            for (int j=0; j< wordLength ; j++)
                resultantWord = resultantWord + Character.toString(tempResultantWord[j]);
            Log.i("word",resultantWord);
            resultantWord = sortLetters(resultantWord);
            if(lettersToWorld.containsKey(resultantWord)){
                result.addAll(lettersToWorld.get(resultantWord));
            }
            return;
        }

        for (int i=start; i<=end && end-i+1 >= wordLength - index; i++)
        {
            tempResultantWord[index] = arr[i];
            getResultantWords(arr, tempResultantWord, i+1, end, index+1, wordLength);
        }
    }
}
