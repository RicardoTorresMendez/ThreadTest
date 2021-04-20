package tests;

import java.util.Random;

public class MultiThreading{
	
	private static int number_of_threads = 2; //peak efficiency where cores == threads
	
	private static int size = 50000000; 
	
	private static double single_efficiency;
	
	
	public static void main(String[] args){
		
		QuickSort sorter = new QuickSort();
		
		System.out.println( "Your computer has " + Runtime.getRuntime().availableProcessors() + " cores" );
		
		int[][] arrays = new int[ number_of_threads ][];
		
		int[] single_array = createArray(size, 0, size);
		
		for( int i=0; i<arrays.length; i++ ){
			arrays[ i ] = createArray(size, 0, size);
		}
		
		
		long startTime = System.nanoTime();
		
		sorter.quickSort(single_array);
		
		single_efficiency = 10000/(( System.nanoTime() - startTime )/1000000.0);
		
		startTime = System.nanoTime();
		
		Thread[] sort = new Thread[ number_of_threads ];
		
		for( int i=0; i<sort.length; i++ ){
			int[] temp = arrays[ i ];
			String thread = "finished " + i;
			sort[ i ] = new Thread( new Runnable(){
				@Override
				public void run() {
					int[] array = temp;
					sorter.quickSort( array );
					System.out.println( thread );
				}
			});
			sort[ i ].start();
		}
		
		
		try{
			for( int i=0; i<sort.length; i++ ){
				sort[ i ].join();
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		
		long endTime = System.nanoTime();
		
		
		System.out.println( "finished in " + ( endTime - startTime )/1000000 + "(ms)" );
		
		double efficiency = ( ( ( number_of_threads * 10000 )/( ( endTime - startTime )/1000000.0 ) ) / single_efficiency );
		
		System.out.println( "Efficiency (" + number_of_threads + " thread(s)): " + efficiency * 100 +"% of single threaded performance" );
			
		
	}
	
	
	@SuppressWarnings("unused")
	private static void printArray( int[] array ){
		System.out.print( "array: " );
		for(int i=0; i<array.length; i++){
			System.out.print( array[ i ] + " " );
		}
		System.out.println();
	}
	
	private static int[] createArray( int size, int min, int max ){
		
		Random rand = new Random();
		
		int[] array = new int[ size ];
		
		for(int i=0; i<size; i++){
			array[ i ] = rand.nextInt( (max - min) + 1) + min;
		}
		
		return array;
		
	}

}
































