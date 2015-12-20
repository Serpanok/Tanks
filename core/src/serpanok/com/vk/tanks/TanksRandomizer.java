package serpanok.com.vk.tanks;

//рандомайзер
import java.util.Random;

public class TanksRandomizer {
	
	public Random rand = new Random();
	
	public boolean isEvent( double eventCoefficient )
	{
		if( eventCoefficient >= 1 )
		{
			return true;
		}
		else if( eventCoefficient >= 0.1 )
		{
			int randomInt = rand.nextInt( 10 ) + 1;
			
			return ( randomInt <= (int)(eventCoefficient * 10) );
		}
		return false;
	}
	
}
