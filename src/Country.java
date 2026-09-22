import jdk.jshell.spi.SPIResolutionException;

import java.util.ArrayList;
import java.util.List;

public class Country {

    public static List<Country> countryList = new ArrayList<>();

    public Country(){

      id = countryList.size();
      partitionId = countryList.size();
      countryList.add(this);
      Runnable runnable = () -> plusPower();
      Thread th = new Thread(runnable);
      th.start();
      power = maxPower;
    }
    protected int power;
    int countHitTominus = 1;
    private String countryType;

    public void setCountryType(String countryType){this.countryType = countryType; }
    public String getCountryType(){return this.countryType; }
    int countHit;
//    private int color;
    private int id;
    private int partitionId;
    protected boolean doublePower = false;
    private boolean callPlusPower = true;
    protected int secondToGetPower = 2;

    private int countAttackTogetOne = 3;

    private int maxPower = 10;

    private int cathcerId = -1;
    public void setMaxPower(int maxPower) {this.maxPower = maxPower; }

    public int getPower(){return  power; }

    public void setIndexOnMap(int indexOnMap) {
        this.partitionId = indexOnMap;
    }
    public int getPartitionId(){return  partitionId; }
    public void setPower(int power){this.power = power; }

    public void setCathcerId(int cathcerId){this.cathcerId = cathcerId; }
    public int getCathcerId(){return  this.cathcerId; }

    public int getId(){return  id; }

    public void attack(Country attackToCountry){
        System.out.println("in attack");
//        callPlusPower = false;
        power--;

        System.out.println("power: " + power);
//        if(power < 0);
//        {
////            callPlusPower = true;
//            // plusPower should run in another Thread
////            plusPower();
////            return;
//        }


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("minus");
                        if(power <= 0) return;
                        if(attackToCountry.cathcerId == cathcerId){
                            System.out.println("now Heree");
                            attackToCountry.plusPowerFromAnotherCountry();
                        }
                        else{
                            System.out.println("heeeere");
                            attackToCountry.minusPower(cathcerId , doublePower);
                        }

                        attack(attackToCountry);
                    }
                },
                500
        );

    }
    public boolean minusPower(int attackerId , boolean doublePower){
        countHit++;

//        callPlusPower = false;

        int attackPower = 1;
        if(doublePower) attackPower = 2;

        if(countHit % countHitTominus == 0) power -= attackPower;
        System.out.println("dammmage: " + power);
       if(power <= 0) {

           power = 0;

           for(int i = 0; i < Player.players.size(); i++){
               if(Player.players.get(i).getId() == cathcerId){
                   Player.players.get(i).lossCountry(this);
               }
           }
           cathcerId = attackerId;
           for(int i = 0; i < Player.players.size(); i++){
               if(Player.players.get(i).getId() == attackerId){
                  Player.players.get(i).catchCountry(this);
                   System.out.println("country cached by player: " + Player.players.get(i).getCountries().size());
               }
           }

           return true;
       }

       return false;

    }

    public static Country getCountryWithpartitionId(int partitionId){
        Country country = new Country();
        for(int i = 0; i < countryList.size(); i++){
            if(countryList.get(i).getPartitionId() == partitionId){
                country = countryList.get(i);
            }
        }
        return country;
    }

    public void plusPowerFromAnotherCountry(){
        if(power < maxPower) power += 1;
    }

    public void plusPower(){
//        System.out.println("plus: " + id);
        if(power + 1 > maxPower) {
            power = maxPower;
        }
        else power += 1;
//        if(power == maxPower || !callPlusPower) return;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        plusPower();
                    }
                },
                secondToGetPower * 1000
        );
    }


}
