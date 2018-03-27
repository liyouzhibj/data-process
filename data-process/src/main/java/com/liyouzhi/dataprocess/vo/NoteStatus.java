package com.liyouzhi.dataprocess.vo;

public class NoteStatus
{
    private NoteStatus(){};


    private boolean hasStart = false;
    private boolean hasQuotes = false;
    public static NoteStatus getInstance()
    {
        return singleNoteStatus.instance;
    }

    private static class singleNoteStatus{
        private static NoteStatus instance = new NoteStatus();
    }

    public boolean isHasStart() {
        return hasStart;
    }

    public void setHasStart(boolean hasStart) {
        this.hasStart = hasStart;
    }

    public boolean isHasQuotes() {
        return hasQuotes;
    }

    public void setHasQuotes(boolean hasQuotes) {
        this.hasQuotes = hasQuotes;
    }
}
