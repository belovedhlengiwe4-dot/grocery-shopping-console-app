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

    public static void main (String[] args){
        String[] items = {"Milk", "Bread", "Maize Meal", "Maas", "Eggs", "Russians", "Boerewors", "Coffee", "Pasta", "Yogurt", "Potato Chips", "Bananas", "Pineapple"};
        float[] prices = {17.99f, 17.99f, 79.99f, 37.99f, 79.99f, 79.99f, 119.99f, 149.99f, 15.99f, 42.99f, 22.99f, 34.99f, 19.99f};
        String[] purchasedItem = new String[items.length];
        int[] purchasedQuantity = new int[items.length];
        float[] purchasedTotal = new float[items.length];
        int i = 0;
        int j = 0;
        int k = 0;

        Scanner in = new Scanner(System.in);

        while (true){
            System.out.println("============================ Grocery Shop ============================");
            float cartTotal = 0.00f;
            while(true){
                try{
                    System.out.println("Enter the name of the item you want (or type 'finish' to end shopping):");
                    String inputItem = in.nextLine();

                    // Checks if user wants to finish shopping
                    if (inputItem.equalsIgnoreCase("Finish")){
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
                    }

                    // Gets index of item in the array
                    int index = searchItem(items, inputItem);

                    // throw custom exception if item is not found
                    if (index == -1){
                        throw new ItemNotFoundException("Sorry, we couldn't find the item '" + inputItem + "'. Please try again.");
                    }
                    //Add item to purchased array for receipt
                    purchasedItem[i++] = items[index];

                    // Ask for quantity of item
                    System.out.println("Enter the quantity of " + items[index] + " you want to add:");
                    int itemQuantity = in.nextInt();

                    //Add quantity to array for receipt
                    purchasedQuantity[j++] = itemQuantity;

                    in.nextLine();

                    // Calculate total cost of item and add it to cart total
                    float itemCost = prices[index] * itemQuantity;
                    cartTotal += itemCost;

                    //Add item total to array for receipt
                    purchasedTotal[k++] = itemCost;

                    System.out.printf("%d x %s added to cart. Current total: R%.2f%n", itemQuantity, items[index], cartTotal);
                }catch (ItemNotFoundException infe){
                    System.out.println(infe.getMessage());
                }catch (Exception e){
                    System.out.println("Invalid input. Please try again.");
                    in.nextLine();
                }
            }
            System.out.println("To exit shopping cart, type 'exit'.");
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