package be.vdab.dance.exceptions;

public class FestivalNietGevondenException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final long id;
    public FestivalNietGevondenException(long id){
        this.id = id;
    }
    public long getId(){ return id; }
    //pls see pg 27 pdf

}
