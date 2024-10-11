// Code for Stock Trading Simuation in Java
// Authored by: Anshuman Sinha
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

class Stock {
    String stockName;
    double price;
    List<Double> priceHistory = new ArrayList<>();

    public Stock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
        priceHistory.add(price);
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
        priceHistory.add(newPrice);
    }

    public double calculateSMA(int period) {
        if (priceHistory.size() < period) return -1;  // Not enough data
        double sum = 0;
        for (int i = priceHistory.size() - period; i < priceHistory.size(); i++) {
            sum += priceHistory.get(i);
        }
        return sum / period;
    }

    public double calculateEMA(int period) {
        if (priceHistory.size() < period) return -1;  // Not enough data
        double k = 2.0 / (period + 1);
        double ema = priceHistory.get(priceHistory.size() - period);  // Starting point for EMA
        for (int i = priceHistory.size() - period + 1; i < priceHistory.size(); i++) {
            ema = priceHistory.get(i) * k + ema * (1 - k);
        }
        return ema;
    }
}

class User {
    String username;
    String password;
    double balance;
    double marginBalance;
    double debt;
    Map<String, Integer> portfolio = new HashMap<>();
    List<String> transactionHistory = new ArrayList<>();

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.marginBalance = balance * 2;  // 2x margin
        this.debt = 0;
    }

    public void addToPortfolio(String stockSymbol, int quantity) {
        portfolio.put(stockSymbol, portfolio.getOrDefault(stockSymbol, 0) + quantity);
    }

    public void removeFromPortfolio(String stockSymbol, int quantity) {
        if (portfolio.containsKey(stockSymbol)) {
            int currentQuantity = portfolio.get(stockSymbol);
            if (currentQuantity > quantity) {
                portfolio.put(stockSymbol, currentQuantity - quantity);
            } else {
                portfolio.remove(stockSymbol);
            }
        }
    }
}

class Order {
    String stockSymbol;
    double price;
    int quantity;
    String orderType;  // "buy", "sell", "short", "cover", "limit"

    public Order(String stockSymbol, double price, int quantity, String orderType) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.quantity = quantity;
        this.orderType = orderType;
    }
}

public class AdvancedStockTradingSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Stock> market = new ConcurrentHashMap<>();
    static Map<String, User> users = new HashMap<>();
    static PriorityQueue<Order> buyOrders = new PriorityQueue<>(Comparator.comparingDouble(o -> -o.price));  // Max heap
    static PriorityQueue<Order> sellOrders = new PriorityQueue<>(Comparator.comparingDouble(o -> o.price));  // Min heap
    static List<Order> stopLossOrders = new ArrayList<>();
    static List<Order> takeProfitOrders = new ArrayList<>();
    static List<String> newsFeed = new ArrayList<>();
    static Timer marketTimer = new Timer();

    public static void main(String[] args) {
        initializeMarket();
        startMarketSimulation();

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            if (choice == 1) {
                registerUser();
            } else if (choice == 2) {
                User user = loginUser();
                if (user != null) {
                    displayUserMenu(user);
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }
        }
    }

    static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("TSLA", new Stock("TSLA", 800));
        market.put("GOOG", new Stock("GOOG", 2800));
        market.put("MSFT", new Stock("MSFT", 300));

        // Initialize some news
        newsFeed.add("Apple announces new iPhone.");
        newsFeed.add("Tesla achieves record sales.");
        newsFeed.add("Google acquires AI startup.");
        newsFeed.add("Microsoft launches new Surface product.");
    }

    static void registerUser() {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        users.put(username, new User(username, password, 10000));  // Initial balance of $10,000
        System.out.println("User registered successfully!");
    }

    static User loginUser() {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            System.out.println("Login successful!");
            return users.get(username);
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }

    static void displayUserMenu(User user) {
        while (true) {
            System.out.println("\n1. View Portfolio\n2. View Market\n3. Buy Stock\n4. Sell Stock\n5. Add Funds");
            System.out.println("6. Margin Trading\n7. Short Selling\n8. Place Limit Order\n9. Market News");
            System.out.println("10. Market Analytics\n11. Transaction History\n12. View Technical Indicators\n13. Logout");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewPortfolio(user);
                    break;
                case 2:
                    viewMarket();
                    break;
                case 3:
                    buyStock(user);
                    break;
                case 4:
                    sellStock(user);
                    break;
                case 5:
                    addFunds(user);
                    break;
                case 6:
                    marginTrading(user);
                    break;
                case 7:
                    shortSelling(user);
                    break;
                case 8:
                    placeLimitOrder(user);
                    break;
                case 9:
                    displayMarketNews();
                    break;
                case 10:
                    displayMarketAnalytics();
                    break;
                case 11:
                    viewTransactionHistory(user);
                    break;
                case 12:
                    viewTechnicalIndicators(user);
                    break;
                case 13:
                    System.out.println("Logging out...");
                    return;
            }
        }
    }

    // New Functionalities

    static void marginTrading(User user) {
        System.out.println("\nEnter stock symbol to buy on margin:");
        String stockSymbol = scanner.next();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        Stock stock = market.get(stockSymbol);

        if (stock != null) {
            double totalPrice = stock.price * quantity;
            if (user.marginBalance >= totalPrice) {
                user.marginBalance -= totalPrice;
                user.debt += totalPrice;
                user.addToPortfolio(stockSymbol, quantity);
                System.out.println("Bought " + quantity + " shares of " + stockSymbol + " on margin for $" + totalPrice);
            } else {
                System.out.println("Insufficient margin balance.");
            }
        } else {
            System.out.println("Stock not found.");
        }
    }

    static void shortSelling(User user) {
        System.out.println("\nEnter stock symbol to short sell:");
        String stockSymbol = scanner.next();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        Stock stock = market.get(stockSymbol);

        if (stock != null) {
            double totalPrice = stock.price * quantity;
            user.balance += totalPrice;  // User receives money for selling
            user.debt += totalPrice;     // User now has debt (needs to cover later)
            System.out.println("Short sold " + quantity + " shares of " + stockSymbol + " for $" + totalPrice);
        } else {
            System.out.println("Stock not found.");
        }
    }

    static void placeLimitOrder(User user) {
        System.out.println("\nEnter stock symbol:");
        String stockSymbol = scanner.next();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        System.out.println("Enter limit price:");
        double limitPrice = scanner.nextDouble();
        System.out.println("Enter order type (buy/sell):");
        String orderType = scanner.next();

        Order order = new Order(stockSymbol, limitPrice, quantity, orderType);
        if (orderType.equals("buy")) {
            buyOrders.add(order);
        } else if (orderType.equals("sell")) {
            sellOrders.add(order);
        }
        System.out.println("Limit order placed for " + quantity + " shares of " + stockSymbol + " at $" + limitPrice);
    }

    static void displayMarketNews() {
        System.out.println("\nMarket News:");
        for (String news : newsFeed) {
            System.out.println(news);
        }
    }

    static void displayMarketAnalytics() {
        Stock topPerformer = null;
        Stock worstPerformer = null;

        for (Stock stock : market.values()) {
            if (topPerformer == null || stock.price > topPerformer.price) {
                topPerformer = stock;
            }
            if (worstPerformer == null || stock.price < worstPerformer.price) {
                worstPerformer = stock;
            }
        }

        System.out.println("\nMarket Analytics:");
        System.out.println("Top Performer: " + topPerformer.stockName + " at $" + topPerformer.price);
        System.out.println("Worst Performer: " + worstPerformer.stockName + " at $" + worstPerformer.price);
    }

    static void viewTransactionHistory(User user) {
        System.out.println("\nTransaction History:");
        for (String transaction : user.transactionHistory) {
            System.out.println(transaction);
        }
    }

    static void viewTechnicalIndicators(User user) {
        System.out.println("\nEnter stock symbol:");
        String stockSymbol = scanner.next();
        Stock stock = market.get(stockSymbol);

        if (stock != null) {
            System.out.println("Enter period for SMA:");
            int smaPeriod = scanner.nextInt();
            double sma = stock.calculateSMA(smaPeriod);
            System.out.println("Simple Moving Average (SMA) for " + stockSymbol + " over " + smaPeriod + " periods: $" + sma);

            System.out.println("Enter period for EMA:");
            int emaPeriod = scanner.nextInt();
            double ema = stock.calculateEMA(emaPeriod);
            System.out.println("Exponential Moving Average (EMA) for " + stockSymbol + " over " + emaPeriod + " periods: $" + ema);
        } else {
            System.out.println("Stock not found.");
        }
    }

    // Existing Functionalities

    static void viewPortfolio(User user) {
        System.out.println("\nPortfolio:");
        double totalValue = 0;
        for (Map.Entry<String, Integer> entry : user.portfolio.entrySet()) {
            String stockSymbol = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = market.get(stockSymbol);
            totalValue += stock.price * quantity;
            System.out.println(stockSymbol + ": " + quantity + " shares at $" + stock.price);
        }
        System.out.println("Total Portfolio Value: $" + totalValue);
    }

    static void viewMarket() {
        System.out.println("\nMarket:");
        for (Stock stock : market.values()) {
            System.out.println(stock.stockName + ": $" + stock.price);
        }
    }

    static void buyStock(User user) {
        System.out.println("\nEnter stock symbol to buy:");
        String stockSymbol = scanner.next();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        Stock stock = market.get(stockSymbol);

        if (stock != null) {
            double totalPrice = stock.price * quantity;
            if (user.balance >= totalPrice) {
                user.balance -= totalPrice;
                user.addToPortfolio(stockSymbol, quantity);
                user.transactionHistory.add("Bought " + quantity + " shares of " + stockSymbol + " at $" + stock.price);
                System.out.println("Bought " + quantity + " shares of " + stockSymbol + " for $" + totalPrice);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Stock not found.");
        }
    }

    static void sellStock(User user) {
        System.out.println("\nEnter stock symbol to sell:");
        String stockSymbol = scanner.next();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();

        if (user.portfolio.containsKey(stockSymbol) && user.portfolio.get(stockSymbol) >= quantity) {
            Stock stock = market.get(stockSymbol);
            double totalPrice = stock.price * quantity;
            user.balance += totalPrice;
            user.removeFromPortfolio(stockSymbol, quantity);
            user.transactionHistory.add("Sold " + quantity + " shares of " + stockSymbol + " at $" + stock.price);
            System.out.println("Sold " + quantity + " shares of " + stockSymbol + " for $" + totalPrice);
        } else {
            System.out.println("Insufficient shares in portfolio.");
        }
    }

    static void addFunds(User user) {
        System.out.println("\nEnter amount to add:");
        double amount = scanner.nextDouble();
        user.balance += amount;
        System.out.println("Added $" + amount + " to your account. New balance: $" + user.balance);
    }

    // Order matching engine and market simulator
    static void matchOrders() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();
            Order sellOrder = sellOrders.peek();

            if (buyOrder.price >= sellOrder.price) {
                int tradedQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);
                System.out.println("Matched order for " + tradedQuantity + " shares at $" + sellOrder.price);

                buyOrders.poll();
                sellOrders.poll();
            } else {
                break;
            }
        }
    }

    static void startMarketSimulation() {
        TimerTask task = new TimerTask() {
            public void run() {
                Random rand = new Random();
                for (Stock stock : market.values()) {
                    double change = rand.nextDouble() * 10 - 5;  // Simulate price changes between -5 to +5
                    stock.updatePrice(stock.price + change);
                }
            }
        };
        marketTimer.scheduleAtFixedRate(task, 0, 5000);  // Update every 5 seconds
    }
}
