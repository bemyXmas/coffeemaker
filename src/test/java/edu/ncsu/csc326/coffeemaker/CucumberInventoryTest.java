package edu.ncsu.csc326.coffeemaker;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Test;
import java.util.Objects;
import static org.junit.Assert.*;

public class CucumberInventoryTest {

    private CoffeeMaker coffeeMaker;
    private InventoryException inventoryException;
    Inventory inventory;

    @Given("coffee maker is on")
    public void coffeeMakerIsOn() {
        coffeeMaker = new CoffeeMaker();
        inventory = new Inventory();
    }

    @When("add {word} coffee")
    public void addCoffee(String coffee_amount) throws InventoryException{
        inventory.addCoffee(coffee_amount);
    }

    @When("add {word} milk")
    public void addMilk(String milk_amount) throws InventoryException{
        inventory.addMilk(milk_amount);
    }

    @When("add {word} chocolate")
    public void addChocolate(String chocolate_amount) throws InventoryException{
        inventory.addChocolate(chocolate_amount);
    }

    @When("add {word} sugar")
    public void addSugar(String sugar_amount) throws InventoryException{
        inventory.addSugar(sugar_amount);
    }

    @Then("Now inventory has {int} {word}")
    public void nowInventoryHasCoffee(int amount, String ingredient) {
        switch (ingredient){
            case "coffee":
                assertEquals(amount, inventory.getCoffee());
                break;
            case "milk":
                assertEquals(amount, inventory.getMilk());
                break;
            case "chocolate":
                assertEquals(amount, inventory.getChocolate());
                break;
            case "sugar":
                assertEquals(amount, inventory.getSugar());
                break;
        }
    }
}