/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installment.calculator;

import java.util.Date;
import installment.enums.PayFrequency;

/**
 *
 * @author bryan
 */
public abstract class Loan {
    
    protected int principal, payFrequency, numPayments, daysInYear; 
    protected double apr, periodInterest, paymentAmt, interestPayAmt, principalPayAmt;
    protected Date origDate, dueDate, semiMonthlyFirstDate, semiMonthlySecondDate;
    
    public Loan(int principal, int payFreq, double apr, double periodInt, Date origDate) {
        
        this.principal = principal;
        this.payFrequency = payFreq;
        this.apr = apr;
        this.periodInterest = periodInt;
        this.origDate = origDate;
    }
    
    public double getApr() {return apr;}
    public void setApr(double apr) {this.apr = apr;}
    
    public int getDaysInYear() {return daysInYear;}
    public void setDaysInYear(int daysInYear) {this.daysInYear = daysInYear;}
    
    public Date getDueDate() {return dueDate;}
    public void setDueDate(Date dueDate) {this.dueDate = dueDate;}
    
    public double getInterestPayAmt() {return interestPayAmt;}
    public void setInterestPayAmt(double interestAmnt) {this.interestPayAmt = interestAmnt;}
    
    public int getNumPayments() {return numPayments;}
    public void setNumPayments(int numPayments) {this.numPayments = numPayments;}
    
    public Date getOrigDate() {return origDate;}
    public void setOrigDate(Date origDate) {this.origDate = origDate;}
    
    public double getPaymentAmt() {return paymentAmt;}
    public void setPaymentAmt(double paymentAmt) {this.paymentAmt = paymentAmt;}

    public int getPayFrequency() {return payFrequency;}
    public void setPayFrequency(int payFrequency) {this.payFrequency = payFrequency;}
    
    public double getPeriodInterest() {return periodInterest;}
    public abstract void setPeriodInterest();

    public int getPrincipal() {return principal;}
    public void setPrincipal(int principal) {this.principal = principal;}
    
    public double getPrincipalPayAmt() {return principalPayAmt;}
    public void setPrincipalPayAmt(double principalPayAmt) {this.principalPayAmt = principalPayAmt;}
    
    public Date getSemiMonthlyFirstDate() {return semiMonthlyFirstDate;}
    public void setSemiMonthlyFirstDate(Date semiMonthlyFirstDate) {this.semiMonthlyFirstDate = semiMonthlyFirstDate;}
    
    public Date getSemiMonthlySecondDate() {return semiMonthlySecondDate;}
    public void setSemiMonthlySecondDate(Date semiMonthlySecondDate) {this.semiMonthlySecondDate = semiMonthlySecondDate;}

}
