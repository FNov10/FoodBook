/*Food.java class
 *Version 1.0
 * 26/8/2022
 * Food class contains details of a standard food, used for initialization and storing in the arrayadapter
 */

package com.example.foodbook;

public class Food {
    private String description;
    private String bestbefore;
    private String Location;
    private int count;
    private int UnitCost;
    static int TotalCost;

    public Food() {
    }

    public Food(String description, String bestbefore, String location, int count, int unitCost) {
        this.description = description;
        this.bestbefore = bestbefore;
        Location = location;
        this.count = count;
        UnitCost = unitCost;
    }

    public Food(String description, String location, int count, int unitCost) {
        this.description = description;
        this.Location = location;
        this.count = count;
        this.UnitCost = unitCost;
    }

    public Food(String description, int count, int unitCost) {
        this.description = description;
        this.count = count;
        this.UnitCost = unitCost;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBestbefore() {
        return this.bestbefore;
    }

    public void setBestbefore(String bestbefore) {
        this.bestbefore = bestbefore;
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnitCost() {
        return this.UnitCost;
    }

    public void setUnitCost(int unitCost) {
        this.UnitCost = unitCost;
    }

    /* Calculates total cost for a food object */
    public void CalculateTotal(){
        TotalCost = TotalCost+ getUnitCost()*getCount();

    }
    public int getTotalCost(){
        return TotalCost;
    }

    /*Edit a total cost in case of any changes */
    public void editTotalCost(int oldtotal){
        TotalCost = TotalCost- oldtotal;

    }


}

