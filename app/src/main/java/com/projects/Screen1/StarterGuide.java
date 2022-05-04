package com.projects.Screen1;

import android.widget.TextSwitcher;

import unit4.collectionsLib.Node;

public class StarterGuide {

    private Node<Integer> layouts, progress;

    public StarterGuide(){
        layouts = new Node<>(R.string.guide_text1,
                new Node<>(R.string.guide_text2,
                        new Node<>(R.string.guide_text3,
                                new Node<>(R.string.guide_text4))));
        progress = layouts;
    }

    public Integer moveForth(){
        progress = progress.getNext();
        if(progress != null) {
            return progress.getValue();
        }
        return null;
    }

    public Node<Integer> getProgress(){
        return (progress != null) ?
                new Node<Integer>(progress.getValue(), progress.getNext()) :
                null;
    }

    public void resetProgress(){
        progress = layouts;
    }
}