/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installment.enums;

/**
 *
 * @author bryan
 */
public enum PayFrequency {
    WEEKLY(7), BI_WEEKLY(14), SEMI_MONTHLY(15), MONTHLY(30);

    private int duration;
    private int loanPeriod;

    PayFrequency(int duration){
        this.duration = duration;

        switch(this.ordinal()){
            case 0:
                loanPeriod = duration;
                break;
            case 1:
                loanPeriod = duration;
                break;
            case 2:
                loanPeriod = duration;
                break;
            case 3:
                loanPeriod = duration;
                break;
        }
    }

    public int getDuration(){return duration;}

    public int getLoanPeriod(){return loanPeriod;}
}
