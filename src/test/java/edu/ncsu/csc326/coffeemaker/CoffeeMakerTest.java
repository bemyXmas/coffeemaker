/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sirapop Kunjiak
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	private CoffeeMaker mockCoffeeMaker;
	private RecipeBook mockRecipeBook;
	private Recipe[] recipeList;
	private Inventory inventory;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");

		mockRecipeBook = mock(RecipeBook.class);
		inventory = new Inventory();

		recipeList = new Recipe[]{recipe1, recipe2, recipe3, recipe4};
		mockCoffeeMaker = new CoffeeMaker(mockRecipeBook, inventory);
	}

	/**
	 * Test adding the valid recipe
	 */
	@Test
	public void testAddRecipe() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
	}

	/**
	 * The recipe should not be added
	 * When we add a recipe over three.
	 */
	@Test
	public void testAddRecipeOverThree() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
		assertFalse(coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Test add a recipe which is already exist
	 * and the recipe should not be added
	 */
	@Test
	public void testAddDuplicateRecipe() {
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}

	/**
	 * Add a recipe and delete it by the index
	 */
	@Test
	public void testDeleteRecipe() {
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.deleteRecipe(0);
		assertNotEquals(recipe2, coffeeMaker.getRecipes()[0]);
		assertNull(coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Deleting a recipe that not added yet so it won't be exist
	 */
	@Test
	public void testDeleteRecipeNotAddedRecipe() {
		assertNull(coffeeMaker.deleteRecipe(0));
	}


	/**
	 * Test add a recipe and then edit it
	 */
	@Test
	public void testEditRecipe() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1.getName(), coffeeMaker.editRecipe(0, recipe2));
		assertEquals(recipe2, coffeeMaker.getRecipes()[0]);
	}



	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
		coffeeMaker.addInventory("5", "3","1","2");
		coffeeMaker.addInventory("4", "3","2","1");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
		coffeeMaker.addInventory("-4", "2", "twotwo", "1");
	}

	/**
	 * Test if the inventory is update when add inventory and check inventory
	 */
	@Test
	public void testCheckInventory() throws InventoryException {
		assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
		coffeeMaker.addInventory("2","2","2","2");
		assertEquals("Coffee: 17\nMilk: 17\nSugar: 17\nChocolate: 17\n", coffeeMaker.checkInventory());
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * Try a negative price for make coffee
	 */
	@Test
	public void testMakeCoffeePriceNegative() {
		coffeeMaker.addRecipe(recipe3);
		assertEquals(-150, coffeeMaker.makeCoffee(0, -150));
	}


	/**
	 * Try Zero money purchase
	 */
	@Test
	public void testMakeCoffeeZeroMoney() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(0, coffeeMaker.makeCoffee(0, 0));
	}

	/**
	 * Test not added a recipe and then edit it
	 */
	@Test
	public void testEditNotAddedRecipe() {
		assertNull(coffeeMaker.editRecipe(0, recipe1));
	}

	/**
	 * Test check inventory after finish a purchasing phase
	 */
	@Test
	public void testAfterPurchaseInventoryCheck() {
		coffeeMaker.addRecipe(recipe4);
		assertEquals(25, coffeeMaker.makeCoffee(0, 150));
		assertEquals("Coffee: 10\nMilk: 13\nSugar: 9\nChocolate: 15\n", coffeeMaker.checkInventory());
	}

	/**
	 * Make a coffee with null recipe
	 * Then we get our money back.
	 */
	@Test
	public void testMakeCoffeeWithNullRecipe() {
		int change = coffeeMaker.makeCoffee(0, 100);
		assertEquals(100, change);
	}

	/**
	 * When we make a coffee, if there is not enough inventory to make a coffee
	 * Then we will get our money back.
	 */
	@Test
	public void testNotEnoughInventory() {
		coffeeMaker.addRecipe(recipe2);
		int change = coffeeMaker.makeCoffee(0, 75);
		assertEquals(75, change);
	}

	/**
	 * Test the amount of times getRecipes() is being called.
	 */
	@Test
	public void testMockPurchaseBevWithEnoughIngredientAndEnoughMoney() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		int change = mockCoffeeMaker.makeCoffee(0, 60);
		assertEquals(10, change);
		verify(mockRecipeBook, times(4)).getRecipes();
	}

	/**
	 * Test the amount of times getRecipes() is being called.
	 */
	@Test
	public void testMockPurchaseBevWithNotEnoughMoney() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		int change = mockCoffeeMaker.makeCoffee(0, 50);
		assertEquals(0, change);
		verify(mockRecipeBook, times(4)).getRecipes();
	}

	/**
	 * Test the amount of times getRecipes() is being called.
	 */
	@Test
	public void testMockPurchaseBevWithNotEnoughMoneyAndNotEnoughIngredient() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		int change = mockCoffeeMaker.makeCoffee(0, 45);
		assertEquals(45, change);
		verify(mockRecipeBook, times(2)).getRecipes();
	}

	/**
	 * Test the amount of times getRecipes() is being called.
	 */
	@Test
	public void testMockPurchaseBevWithNotEnoughIngredient() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		int change = mockCoffeeMaker.makeCoffee(1, 95);
		assertEquals(95, change);
		verify(mockRecipeBook, times(3)).getRecipes();
	}

	/**
	 * Test the amount of times getRecipes() is being called.
	 */
	@Test
	public void testMockPurchaseBevWithNoRecipe() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		int change = mockCoffeeMaker.makeCoffee(2, 40);
		assertEquals(40, change);
		verify(mockRecipeBook, times(2)).getRecipes();
	}

	/**
	 * The units of each items in the inventory are displayed (string)
	 * default amount of all ingredients are 15
	 */
	@Test
	public void testMockInventoryWhenBevIsPurchase() {
		when(mockRecipeBook.getRecipes()).thenReturn(recipeList);
		mockCoffeeMaker.makeCoffee(0, 100);
		StringBuffer s1 = new StringBuffer();
		s1.append("Coffee: ");
		s1.append("12");
		s1.append("\n");
		s1.append("Milk: ");
		s1.append("14");
		s1.append("\n");
		s1.append("Sugar: ");
		s1.append("14");
		s1.append("\n");
		s1.append("Chocolate: ");
		s1.append("15");
		s1.append("\n");
		assertEquals(s1.toString(), mockCoffeeMaker.checkInventory());
	}
}
