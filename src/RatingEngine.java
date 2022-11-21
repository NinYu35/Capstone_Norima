/**
 * Norima Course 4, Module 3, Java Certification Project
 * Norima Java Developer Course Capstone Project
 *
 * @author : Niño Galanida
 * Date Created : 06/23/2022
 */

import java.text.DecimalFormat;
import java.time.LocalDate;

public class RatingEngine {
    public double vpf;
    public int ageOfVehicle;

    /**
     * Computation for vehicle price factor
     * @param year integer parameter of vehicle's purchased year from vehicle class
     * @return the value of vpf
     */
    public double getVpf(int year) {
        ageOfVehicle = LocalDate.now().getYear() - year;
        if (ageOfVehicle < 1) {
            return 0.01;
        }
        if (ageOfVehicle < 3) {
            return 0.008;
        }
        if (ageOfVehicle < 5) {
            return 0.007;
        }
        if (ageOfVehicle < 10) {
            return 0.006;
        }
        if (ageOfVehicle < 15) {
            return 0.004;
        }
        if (ageOfVehicle < 20) {
            return 0.002;
        }
        if (ageOfVehicle < 40) {

            return 0.001;
        }
        return vpf;

    }

    /**
     * Computation of premium for each vehicle in a policy
     * @param purchasePrice double parameter for purchase price from vehicle class
     * @param year integer parameter for vehicle's purchased year
     * @param dlx double parameter of number of years since driver's license was first issued
     * @return formatted (with two decimal place) premium charged
     */
    public double calculatePremium(double purchasePrice, int year, double dlx) {
        DecimalFormat df = new DecimalFormat("#.##");
        double charged = (purchasePrice * getVpf(year)) + ((purchasePrice / 100) / dlx);
        return Double.parseDouble(df.format(charged));
    }
}
