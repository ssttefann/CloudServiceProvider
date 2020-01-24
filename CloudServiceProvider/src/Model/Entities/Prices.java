package Model.Entities;

public class Prices {
    private static double hoursInMonth = 720;
    public static double monthlyPricePerGBForSSD = 0.3;
    public static double monthlyPricePerGBForHDD = 0.1;
    public static double monthlyPricePerCpu = 25;
    public static double monthlyPricePerGBOfRam = 15;
    public static double monthlyPricePerGPU = 1;
    public static double hourlyPricePerGBForSSD = monthlyPricePerGBForSSD / hoursInMonth;
    public static double hourlyPricePerGBForHDD = monthlyPricePerGBForHDD / hoursInMonth;
    public static double hourlyPricePerCpu = monthlyPricePerCpu / hoursInMonth;
    public static double hourlyPricePerGBOfRam = monthlyPricePerGBOfRam / hoursInMonth;
    public static double hourlyPricePerGPU = monthlyPricePerGPU / hoursInMonth;
}
