package fi.swarco.testPrograms;

    import java.util.*;
        import java.lang.*;
        import java.io.*;
        import java.time.Instant;
        import java.util.Random;

/* Name of the class has to be "Main" only if the class is public. */
public class Test1
{
    public static void main (String[] args) throws java.lang.Exception
    {
        System.out.println( "Starting at: " + Instant.now() );
        Random rand = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            String date = "2016-10-26T12:31:39.0847";

            for (int j = 0; j < rand.nextInt(6); j++) {
                date += rand.nextInt(10);
            }

            if( ( i % 100_000 ) == 0 ) {
                System.out.println( "So far: " + i + " | date: " + date ) ;
            }

            date += "Z";
            date = date.replace("26", "" + (rand.nextInt(20) + 10));

            Instant instant = Instant.parse(date);
            Long epoch = instant.getEpochSecond();
            // System.out.println(epoch);  // Exceeds limit of IdeOne.com.
        }
        System.out.println( "Done. Now: " + Instant.now() );
    }
}


// they try
//        LocalDateTime localDateTime = LocalDateTime.from(new Date().toInstant());
//         to resolve the issue, please pass in Zone -
//         LocalDateTime localDateTime = LocalDateTime.from(new Date()
//         .toInstant().atZone(ZoneId.of("UTC")));
