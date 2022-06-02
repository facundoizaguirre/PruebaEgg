class Factorial {
 
   public static void main( String[] args ) {
      int numero = 6;
      System.out.println( "Ahora estoy haciendo OTRA prueba para git");

      System.out.println( "Ejemplo del Cálculo del factorial de un número en Java" );
      System.out.println( "El factorial de " + numero + " es: " + factorial( numero ) );      
      
   }
 
   public static int factorial( int numero ) {
      int fact = 1;
      for( int i = 1; i <= numero; i++ ) {
         fact *= i;
      }
 
      return fact;
    }
}