import java.util.Scanner;
public class GroceryShopping{

    public static int searchItem(String[] items, String targetItem){
        int index = -1;
        for (int i = 0; i < items.length; i++){
            if (items[i].equalsIgnoreCase(targetItem)){
                index = i;
                break;
            }
        }
        return index;
    }

    public static void filterItemsBelowPrice(String[] items, float[] prices, float thresholdPrice){
        int itemsFound = 0;
        for (int i = 0; i < prices.length; i++){
            if (prices[i] < thresholdPrice){
                System.out.printf("%-20s R%.2f%n", items[i], prices[i]);
                itemsFound++;
            }
        }
        if (itemsFound == 0){
            System.out.println("No items found.");
        }
    }

    public static void main (String[] args){
        String[] items = {"Milk", "Bread", "Maize Meal", "Maas", "Eggs", "Russians", "Boerewors", "Coffee", "Pasta", "Yogurt", "Potato Chips", "Bananas", "Pineapple"};
        float[] prices = {17.99f, 17.99f, 79.99f, 37.99f, 79.99f, 79.99f, 119.99f, 149.99f, 15.99f, 42.99f, 22.99f, 34.99f, 19.99f};

        Scanner in = new Scanner(System.in);

        while (true){
            System.out.println("============================ Grocery Shop ============================");
            float cartTotal = 0.00f;
            String[] purchasedItem = new String[items.length];
            int[] purchasedQuantity = new int[items.length];
            float[] purchasedTotal = new float[items.length];
            int i = 0;
            int j = 0;
            int k = 0;
            while(true){
                try{
                    System.out.println("Enter the name of the item you want" + 
                    "\nor type 'filter' to browse cheaper items" +
                    "\nor type 'finish' to end shopping");
                    String inputItem = in.nextLine();

                    //Checks if user wants to browse for cheaper options
                    if (inputItem.equalsIgnoreCase("Filter")){
                        System.out.println("Enter maximum price: ");
                        float maxPrice = Float.parseFloat(in.nextLine());

                        if (maxPrice > 0){
                            System.out.printf("---- Items under R%.2f ----%n", maxPrice);
                            filterItemsBelowPrice(items, prices, maxPrice);
                            System.out.println("----------------------------");
                        } else{
                            System.out.println("Please enter a price that is greater than 0.");
                        }
                    }
                    // Checks if user wants to finish shopping
                    else if (inputItem.equalsIgnoreCase("Finish")){
                        System.out.println("===================================");
                        System.out.println("           GROCERY SHOP            ");
                        System.out.println("===================================");
                        System.out.println();
                        
                        for (int p = 0; p < purchasedItem.length; p++){
                            if (purchasedItem[p] != null){
                                String line = purchasedItem[p] + " x " + purchasedQuantity[p];
                                while (line.length() < 27){
                                    line+=".";
                                }
                                System.out.printf("%-27s R%.2f%n", line , purchasedTotal[p]);
                            }
                        }
                        System.out.println();
                        System.out.println("-----------------------------------");
                        System.out.printf("Total                       R%.2f%n", cartTotal);
                        System.out.println();
                        System.out.println("Thank you for shopping with us.");
                        break;
                    }else{
                        // Gets index of item in the array
                        int index = searchItem(items, inputItem);

                        // throw custom exception if item is not found
                        if (index == -1){
                            throw new ItemNotFoundException("Sorry, we couldn't find the item '" + inputItem + "'. Please try again.");
                        }
                        // Ask for quantity of item
                        System.out.println("Enter the quantity of " + items[index] + " you want to add:");
                        int itemQuantity = Integer.parseInt(in.nextLine());

                        if (itemQuantity > 0){
                            //Add item to and quantity to arrays if quantity is valid
                            purchasedItem[i++] = items[index];
                            purchasedQuantity[j++] = itemQuantity;

                            // Calculate total cost of item and add it to cart total
                            float itemCost = prices[index] * itemQuantity;
                            cartTotal += itemCost;

                            //Add item total to array for receipt
                            purchasedTotal[k++] = itemCost;

                            System.out.printf("%d x %s added to cart. Current total: R%.2f%n", itemQuantity, items[index], cartTotal);
                        }else{
                            System.out.println("Item quantity must be greater than 0. Please try again.");
                        }
                    }
                }catch (ItemNotFoundException infe){
                    System.out.println(infe.getMessage());
                }catch (Exception e){
                    System.out.println("Invalid input. Please try again.");
                }
            }
            System.out.println("Type 'exit' to leave shopping cart or any other key to start over");
            String userInput = in.nextLine();

            // Exit program if user types "Exit"
            if (userInput.equalsIgnoreCase("Exit")){
                System.out.println("Thank you for using the shopping cart. Have a great day!");
                break;
            }
        }
        in.close();
    }
}