/* Assignment */

import java.io.*;
import java.util.*;

/**
* Utility class to read the csv file and provide the output
* of each company highest price in history.
* @author Amit Jain
*/
public class  CSVReader
{
	public static void main(String[] args) 
	{
		List[] companies = null;
		try
		{
			File file = new File("StockData.csv");	
			BufferedReader br = new BufferedReader(new FileReader(file));	// Read the csv fie
			boolean flag = true;
			String data = null;
			while(((data = br.readLine()) != null)) // read file line by line
			{
				String[] array = data.split(","); // split the line separated by commas
				String year = null;
				String month = null;
				Company company = null;
				double price = 0;

				// this will be called only one time, to parse the header and generate the companies list
				if(flag) 
				{
					flag = false;
					if(array == null || array.length < 3)
					{
						throw new Exception("Invalid Data...");
					}
					companies = new List[array.length - 2];
					for(int i = 0; i < (array.length -2) ; i++) {
						companies[i] = new ArrayList();
					}
				}
				else 
				{
					for(int a = 0; a < array.length; a++) //assign each line data to respective companies list
					{
						if(a == 0) { // First is always a year
							year = array[a];
						} else if(a==1) {
							month = array[a]; // Next is always a month
						}
						for(int i = 0; i < companies.length; i++)
						{
							if(i == (a-2))
							{
								price = Double.parseDouble(array[a]);
								company = new Company(year, month, price);
								companies[i].add(company);
								break;
							}
						}
					}
				}
			} // eof while
		
			for(int i = 0; i < companies.length; i++)
			{
				Collections.sort(companies[i]); // Sort the companies list
				System.out.println("Company["+i+"] Highest Price on "+(companies[i].get(0).toString())); // Show the output to console
			}
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
}

/**
* Domain class representing each company price details
*/
class Company implements Comparable
{
	String year;
	String month;
	double price;

	public Company(String year, String month, double price)
	{
		this.year = year;
		this.month = month;
		this.price = price;	
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Called in doing sort
	public int compareTo(Object o) {
		if(o != null && o instanceof Company) {
			Company other = (Company) o;
			return ((int)other.getPrice() - (int)this.getPrice());
		}

		return 0;
	}

	// To produce readable output.
	public String toString()
	{
		return "Year-"+this.year+" Month-"+this.month+" Price-"+this.price;
	}

}