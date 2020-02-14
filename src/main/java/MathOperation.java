public class MathOperation implements Operation
{
    Operation leftside;
    Operation rightside;
    String operator;

    public MathOperation(Operation leftside, Operation rightside, String operator)
    {
        this.leftside = leftside;
        this.operator = operator;
        this.rightside = rightside;
    }

    @Override
    public String getResult()
    {
        double result = 0;
        String leftresult = leftside.getResult();
        String rightresult = rightside.getResult();
        switch (operator)
        {
            case "+":
                result = Double.parseDouble(leftresult) + Double.parseDouble(rightresult);
                break;

            case "-":
                result = Double.parseDouble(leftresult) - Double.parseDouble(rightresult);
                break;

            case "*":
                result = Double.parseDouble(leftresult) * Double.parseDouble(rightresult);
                break;

            case "/":
                result = Double.parseDouble(leftresult) / Double.parseDouble(rightresult);
                break;

            case "pow":
                result = Math.pow(Double.parseDouble(leftresult), Double.parseDouble(rightresult));
                break;

        }
        String resultString = String.valueOf(result);
        return resultString;

    }
}
