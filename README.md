Software Requirements Specification (SRS) for Stock Trading Simulation System


Table of Contents:
1. Introduction
   - 1.1 Purpose
   - 1.2 Scope
   - 1.3 Definitions, Acronyms, and Abbreviations
   - 1.4 References
   - 1.5 Overview
2. Overall Description
   - 2.1 Product Perspective
   - 2.2 Product Features
   - 2.3 User Classes and Characteristics
   - 2.4 Operating Environment
   - 2.5 Design and Implementation Constraints
   - 2.6 Assumptions and Dependencies
3. System Features
   - 3.1 User Registration and Authentication
   - 3.2 Portfolio Management
   - 3.3 Stock Trading (Buy/Sell, Short Selling, Margin Trading)
   - 3.4 Limit Orders and Order Matching Engine
   - 3.5 Market Simulation and Real-Time Updates
   - 3.6 Market News and Analytics
   - 3.7 Technical Indicators (SMA, EMA)
   - 3.8 Transaction History and Funds Management
4. Non-functional Requirements
   - 4.1 Performance Requirements
   - 4.2 Security Requirements
   - 4.3 Usability Requirements
   - 4.4 Reliability Requirements
   - 4.5 Maintainability
5. External Interface Requirements
6. Other Requirements
7. Appendices

1. Introduction
1.1 Purpose
The purpose of this document is to define the functional and non-functional requirements for the development of a **Stock Trading Simulation System**. The system emulates stock market behavior, enabling users to perform various trading activities (buy/sell, margin trading, short selling), analyze stocks, and manage their portfolios. It provides an educational platform for understanding market operations and trading strategies in a risk-free environment.

1.2 Scope
This project simulates a trading platform with real-time market updates and technical indicators. Users can trade stocks using different methods, manage portfolios, view transaction history, and perform market analysis through analytics and technical indicators. The project includes a market simulation, a priority-queue-based order matching engine, and margin and short selling functionalities.

1.3 Definitions, Acronyms, and Abbreviations
- SMA: Simple Moving Average
- EMA: Exponential Moving Average
- UI: User Interface
- API: Application Programming Interface

1.4 References
- Java SE Documentation
- Data Structures and Algorithms references for priority queues

1.5 Overview
This document provides a detailed description of the system's functionality, user interface, technical requirements, and the environment in which it will operate.

2. Overall Description

2.1 Product Perspective
The system is designed as a terminal-based application where users can simulate real-time stock trading operations. It interacts with users through text-based commands, allowing them to execute trades, manage funds, analyze stocks, and track their portfolios.

2.2 Product Features
Key features include:
- User Authentication: Secure user registration and login with password protection.
- Portfolio Management: Users can manage stock portfolios and monitor total value.
- Stock Trading: Execute buy/sell orders, short selling, and margin trading.
- Order Matching: Match buy and sell orders with priority queues.
- Market Simulation: Stock prices fluctuate in real-time during the session.
- Technical Indicators: View SMA and EMA for stock analysis.
- Market Analytics: Shows top and worst performers in the market.
- Transaction History: Displays a list of previous transactions.
- Funds Management: Add funds to the user account for trading.

2.3 User Classes and Characteristics
The system targets two types of users:
- New Users: Users who want to learn stock trading without real financial risks.
- Experienced Users: Traders who wish to test strategies and perform technical analysis in a simulated environment.

2.4 Operating Environment
The system will run in a terminal environment, supported on any platform with Java installed (Windows, macOS, Linux).

2.5 Design and Implementation Constraints
- The system must be implemented in Java, and only terminal-based interactions are allowed.
- The stock data will be simulated; real-time API integration is outside the current scope.
- High-level efficiency in order matching using priority queues is a requirement.

2.6 Assumptions and Dependencies
- Java must be pre-installed on the user's system.
- Market data will be simulated for this version of the application.

3. System Features

3.1 User Registration and Authentication
- Description: The system allows users to register with a username and password and provides a secure login mechanism.
- Input: Username, Password.
- Process: Validates user credentials.
- Output: Success or failure of login or registration.

3.2 Portfolio Management
- Description: Users can manage their stock portfolios, including viewing current holdings and total portfolio value.
- Input: Stock symbol, quantity.
- Output: Portfolio summary showing the value of each holding.

3.3 Stock Trading (Buy/Sell, Short Selling, Margin Trading)
- Description: The user can execute buy, sell, short-sell, and margin trade operations.
- Input: Stock symbol, quantity, order type.
- Output: Execution confirmation, updated portfolio, and balance.

3.4 Limit Orders and Order Matching Engine
- Description: Users can place limit orders and the system will match buy and sell orders using priority queues.
- Input: Stock symbol, quantity, limit price, order type.
- Output: Order matching notifications and execution status.

3.5 Market Simulation and Real-Time Updates
- Description: The market simulation continuously updates stock prices at regular intervals.
- Process: Uses random fluctuations for stock prices in real-time.
- Output: Updated stock prices displayed periodically.

3.6 Market News and Analytics
- Description: The system displays market news updates and analytics for top and worst-performing stocks.
- Output: News feed and market performance summary.

3.7 Technical Indicators (SMA, EMA)
- Description: Users can view technical indicators (Simple and Exponential Moving Averages) for specific stocks over a user-specified period.
- Input: Stock symbol, period for SMA/EMA.
- Output: Calculated SMA/EMA for the given stock.

3.8 Transaction History and Funds Management
- Description: Users can view their transaction history and manage funds by adding money to their accounts.
- Input: Transaction requests, amount to add.
- Output: Transaction history and updated account balance.

4. Non-functional Requirements

4.1 Performance Requirements
- The system should execute order matching efficiently using priority queues, even with a large number of buy/sell orders.
- Real-time stock price updates should occur every 5 seconds without noticeable lag.

4.2 Security Requirements
- Passwords must be stored securely using hashing algorithms.
- All user data must be kept confidential and only accessible to the authenticated user.

4.3 Usability Requirements
- The terminal-based UI must be intuitive, and commands should be simple and clearly documented.
- Error messages should guide users on invalid inputs or failed transactions.

4.4 Reliability Requirements
- The system should handle up to 1000 active users without performance degradation.
- Transactions should be processed accurately, with consistent market simulation updates.

4.5 Maintainability
- The system code should be modular and well-documented for ease of maintenance and future upgrades.

5. External Interface Requirements
No external APIs or interfaces are required in this version of the system. All data (market prices, news, etc.) will be simulated within the application.

6. Other Requirements
The system may require integration with a financial data API in future versions to provide real-time stock prices and news.
(Open Source Project for Hacktoberfest2024)
