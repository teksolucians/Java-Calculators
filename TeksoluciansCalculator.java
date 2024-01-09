package treasureHuntPackage;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;


public class TeksoluciansCalculator {
	   // Frame and panels
	   static JFrame calFrame = new JFrame("Tekso Calculator");
	   static JPanel inputPane = new JPanel();
	   static JPanel expressionPane = new JPanel(); // New panel for expression label
	   static JPanel opPane = new JPanel();
	   static JPanel ansPane = new JPanel();
	   static JPanel quadPane = new JPanel();
	   static JPanel quadInputsPane = new JPanel();

	   // Components for the calculator
	   static JTextField inputField = new JTextField(20);
	   static JLabel expressionLabel = new JLabel("Expression:"); // New label for expression
	   static JLabel ansLabel = new JLabel("Answer:");
	   static JTextField quadraticEquation = new JTextField(15); // New field for answer

    // Components for the quadratic calculator
    static JTextField answersField = new JTextField(20);
    static JTextField xsquared = new JTextField(8);
    static JTextField xterm = new JTextField(8);
    static JTextField constant = new JTextField(8);

    // Action listener for numeric buttons
    public static class NumericListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            inputField.setText(inputField.getText() + source.getText());
        }
    }

    // Action listeners for operation buttons (+, -, *, /)
    public static class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputField.setText(inputField.getText() + "+");
        }
    }

    public static class SubListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputField.setText(inputField.getText() + "-");
        }
    }

    public static class MulListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputField.setText(inputField.getText() + "*");
        }
    }

    public static class DivListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputField.setText(inputField.getText() + "/");
        }
    }

    // Action listener for the equals button
    public static class EqualsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Perform calculation based on the input
            try {
                String userInput = inputField.getText();
                double result = evaluateExpression(userInput); // Placeholder for expression evaluation
                ansLabel.setText("Answer: " + result);
            } catch (Exception ex) {
                ansLabel.setText("Error");
            }
        }
    }

    // Quadratic calculation methods
    public static class QuadraticListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String aStr = xsquared.getText();
                String bStr = xterm.getText();
                String cStr = constant.getText();

                int a = Integer.parseInt(aStr);
                int b = Integer.parseInt(bStr);
                int c = Integer.parseInt(cStr);

                double[] roots = solveQuadratic(a, b, c);
                if (roots == null) {
                    answersField.setText("No Real Roots");
                } else {
                    answersField.setText("Roots: " + roots[0] + ", " + roots[1]);
                }
            } catch (NumberFormatException ex) {
                answersField.setText("Invalid Input");
            }
        }

        private double[] solveQuadratic(int a, int b, int c) {
        	quadraticEquation.setText(a+"x^2 + "+b+"x + "+c);
            double disc = b * b - 4 * a * c;
            if (disc < 0) return null; // No real roots

            double sqrtDisc = Math.sqrt(disc);
            double root1 = (-b + sqrtDisc) / (2 * a);
            double root2 = (-b - sqrtDisc) / (2 * a);

            return new double[]{root1, root2};
        }
    }

    // Main method to set up the GUI
    public static void main(String[] args) {
        // Frame setup
        calFrame.setSize(800, 600);
        calFrame.setLocationRelativeTo(null); // Center the frame
        calFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting up the regular calculator
        setupCalculator();

        // Setting up the quadratic calculator
        setupQuadraticCalculator();

        // Adding panels to the frame
        calFrame.add(inputPane, BorderLayout.NORTH);
        calFrame.add(opPane, BorderLayout.CENTER);
        calFrame.add(ansPane, BorderLayout.SOUTH);

        // Make the frame visible
        calFrame.setVisible(true);
    }

    private static void setupCalculator() {
    	
    	 // Expression panel layout
        expressionPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        expressionPane.add(expressionLabel);
        expressionPane.add(inputField);

        // Answer panel layout
        ansPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        ansPane.add(quadraticEquation);
        quadraticEquation.setText("Quadratic Equation");
        
        inputPane.add(inputField);
        inputPane.add(ansLabel);
        opPane.setLayout(new GridLayout(5, 3, 5, 5)); // Grid layout for the calculator buttons

        // Add numeric buttons
        for (int i = 0; i <= 9; i++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.addActionListener(new NumericListener());
            opPane.add(btn);
        }

        // Add operation buttons
        String[] operations = {"+", "-", "*", "/", "="};
        for (String op : operations) {
            JButton btn = new JButton(op);
            switch (op) {
                case "+":
                    btn.addActionListener(new AddListener());
                    break;
                case "-":
                    btn.addActionListener(new SubListener());
                    break;
                case "*":
                    btn.addActionListener(new MulListener());
                    break;
                case "/":
                    btn.addActionListener(new DivListener());
                    break;
                case "=":
                    btn.addActionListener(new EqualsListener());
                    break;
            }
            opPane.add(btn);
        }

    }
    private static void addToquadraticEquation(String text) {
        quadraticEquation.setText(quadraticEquation.getText() + text);
    }

    
    static double 
    
    evaluateExpression(String expression)
     
    {
            Stack<Double> operandStack = new Stack<>();
            Stack<Character> operatorStack = new Stack<>();

            for (int i = 0; i < expression.length(); i++) {
                char currentChar = expression.charAt(i);
                if (Character.isDigit(currentChar)) {
                    StringBuilder operand = new StringBuilder();
                    while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                        operand.append(expression.charAt(i++));
                    }
                    // Handle decimal point
                    if (i < expression.length() && expression.charAt(i) == '.') {
                        operand.append('.');
                        i++;
                        while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                            operand.append(expression.charAt(i++));
                        }
                    }
                    operandStack.push(Double.parseDouble(operand.toString()));
                    i--; // Decrement i as it will be incremented again in the loop
                } else if (currentChar == '(') {
                    operatorStack.push(currentChar);
                } else if (currentChar == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        performOperation(operandStack, operatorStack.pop());
                    }
                    operatorStack.pop(); // Remove the '('
                } else if (isOperator(currentChar)) {
                    // While operator stack is not empty and top has higher or equal precedence
                    while (!operatorStack.isEmpty() && hasPrecedence(currentChar, operatorStack.peek())) {
                        performOperation(operandStack, operatorStack.pop());
                    }
                    operatorStack.push(currentChar);
                }
            }

            // Perform remaining operations
            while (!operatorStack.isEmpty()) {
                performOperation(operandStack, operatorStack.pop());
            }

            return operandStack.pop();
        }

        private static boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }

        private static boolean hasPrecedence(char op1, char op2) {
            if (op2 == '(' || op2 == ')') {
                return false;
            }
            // Assume + and - have lower precedence than * and /
            return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
        }

        private static void performOperation(Stack<Double> operandStack, char operator) {
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            switch (operator) {
                case
     
    '+':
                    operandStack.push(operand1 + operand2);
                    break;
                case
     
    '-':
                    operandStack.push(operand1 - operand2);
                    break;
                case
     
    '*':
                    operandStack.push(operand1 * operand2);
                    break;
                case '/':
                    try {
                        operandStack.push(operand1 / operand2);
                    } catch (ArithmeticException e) {
                        operandStack.push(Double.NaN); // Handle division by zero
                    }
                    break;
            }
        }
        // ... (rest of the code remains the same)
    
    
    
    private static void setupQuadraticCalculator() {
        quadInputsPane.setLayout(new GridLayout(1, 3, 5, 5));
        quadInputsPane.add(xsquared);
        quadInputsPane.add(xterm);
        quadInputsPane.add(constant);
        
        xsquared.setText("x^2");
        xterm.setText("x");
        constant.setText("constant");
        
        
        JButton quadButton = new JButton("Solve Quadratic");
        quadButton.addActionListener(new QuadraticListener());

        quadPane.setLayout(new BorderLayout());
        quadPane.add(quadInputsPane, BorderLayout.NORTH);
        quadPane.add(quadButton, BorderLayout.CENTER);
        quadPane.add(answersField, BorderLayout.SOUTH);

        ansPane.add(quadPane);
    }
}
