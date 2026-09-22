import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Player {

    public Player(int id , int indexMainCountry , Color playercolor , int playerPower){
        this.countries = new ArrayList<>();
        this.playerColor = playercolor;
        this.id = id;
        Country mainCountry = Country.countryList.get(indexMainCountry);
        players.add(this);
        countries.add(mainCountry);
        mainCountry.setMaxPower(playerPower);

        mainCountry.setPower(playerPower);
        mainCountry.setCathcerId(id);
        System.out.println("id: " + id);
        System.out.println("Player Country Power: " + countries.get(0).getPower());
    }

    public Color getColor(){
        return this.playerColor;
    }

    public static List<Player> players = new ArrayList<>();
    private List<Country> countries;
    private int id;
    private int playerAttack = 30;

    private int powerForAttack = 0;
    private Color playerColor;
    private String Color;

    public int getId(){return  id; }

    private List<Country> selectedCountryForAttack = new ArrayList<>();

    private void addCountryForAttack(Country country){
        selectedCountryForAttack.add(country);
        powerForAttack += country.getPower();
    }

    public List<Country> getSelectedCountryForAttack() {
        return selectedCountryForAttack;
    }
    public List<Country> getCountries(){ return countries; }


    private void removeCountryForAttack(Country country){
        List<Country> newList = new ArrayList<>();
        for(int i = 0; i < selectedCountryForAttack.size(); i++){
            if(selectedCountryForAttack.get(i).getId() != country.getId()){
                newList.add(selectedCountryForAttack.get(i));
            }
        }

        selectedCountryForAttack = newList;
    }

    public void attack(Country attckedContry){
//        attckedContry.attack(powerForAttack);
//        selectedCountryForAttack.clear();
//        powerForAttack = 0;
        if(powerForAttack < attckedContry.power) return;
        for(int i = 0; i < selectedCountryForAttack.size(); i++){
            System.out.println("start attacking");
            // Needed Thread.
            Country country = selectedCountryForAttack.get(i);
//            country.setPower(30);
//            country.setMaxPower(30);
            System.out.println("playerPower: " + country.getPower());
            Runnable runnable = () -> country.attack(attckedContry);
            Thread th = new Thread(runnable);
            th.start();
//            runnable.start();

        }

        selectedCountryForAttack.clear();
        powerForAttack = 0;
    }

    public void leftClick(Country country){
        boolean attack = true;
        for(int i = 0; i < countries.size(); i++){
            if(countries.get(i).getId() == country.getId()){
                attack = false;
            }
        }

        if(powerForAttack <= country.power && attack) return;

        for(int i = 0; i < selectedCountryForAttack.size(); i++){
            System.out.println("start attacking");
            // Needed Thread.
            Country sCountry = selectedCountryForAttack.get(i);
//            country.setPower(30);
//            country.setMaxPower(30);
//            System.out.println("playerPower: " + sCountry.getPower());
            Runnable runnable = () -> sCountry.attack(country);
            Thread th = new Thread(runnable);
            th.start();
//            runnable.start();

        }

        selectedCountryForAttack.clear();
        powerForAttack = 0;

    }
    public int rightClick(Country country){
        int result = 0;
        boolean isPlayerCountry = false;
        for(int i = 0; i < countries.size(); i++){
            System.out.println("coutryId: " + countries.get(i).getId());
            if(countries.get(i).getId() == country.getId()){
                System.out.println("here is good");
                isPlayerCountry = true;
                break;
//                return;
            }
        }
        boolean addToSelectedCountry = true;
        System.out.println("bool: " + isPlayerCountry);
        if(isPlayerCountry){
            System.out.println("hiiiiiiiiiiiiiiii");
            for(int i = 0; i < selectedCountryForAttack.size(); i++){
                if(selectedCountryForAttack.get(i).getId() == country.getId()){
                    removeCountryForAttack(country);
                    addToSelectedCountry = false;
                    System.out.println("hi");
                }
            }
            if(addToSelectedCountry) {
                addCountryForAttack(country);
                result = 1;
                System.out.println("helllllo");

            }
        }
        else {
            return -1;
        }

        return result;
    }
    public void clickOnCountry(Country country){
        System.out.println("id form player: " + country.getId());
        System.out.println("size: " + Player.players.size());
//        System.out.println("hello");
        boolean isPlayerCountry = false;
        for(int i = 0; i < countries.size(); i++){
            System.out.println("coutryId: " + countries.get(i).getId());
            if(countries.get(i).getId() == country.getId()){
                System.out.println("here is good");
                isPlayerCountry = true;
                break;
//                return;
            }
        }
        boolean addToSelectedCountry = true;
        System.out.println("bool: " + isPlayerCountry);
        if(isPlayerCountry){
            System.out.println("hiiiiiiiiiiiiiiii");
            for(int i = 0; i < selectedCountryForAttack.size(); i++){
                if(selectedCountryForAttack.get(i).getId() == country.getId()){
                    removeCountryForAttack(country);
                    addToSelectedCountry = false;
                    System.out.println("hi");
                }
            }
            if(addToSelectedCountry) {
                addCountryForAttack(country);
                System.out.println("helllllo");

            }
        }
        else{
            System.out.println("how are you");
            attack(country);
        }
//        selectedCountryForAttack.add(country);


    }

    public void catchCountry(Country country){
        countries.add(country);
    }
    public void lossCountry(Country country){
        List<Country> newList = new ArrayList<>();
        for(int i = 0; i< countries.size(); i++){
            if(countries.get(i).getId() != country.getId()){
                newList.add(countries.get(i));
            }
        }

//        countries.remove(country);

        countries = new ArrayList<>();
        countries = newList;
    }



}
