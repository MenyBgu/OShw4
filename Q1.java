/*MenyBuganim 302490610___BaruchRothkoff 311603252*/

import java.util.Scanner;
import java.util.Arrays;

public class Q1 {
	public static boolean checkHelp(int[] available, int[] N) {
		for (int i = 0; i < N.length; i++) {
			if (N[i] > available[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean[] checkDeadlock(int[][] A, int[][] C,int[] available, boolean[] flags) {
		int[] available2 = new int[available.length];
		for (int i = 0; i < available2.length; i++) {
			available2[i] = available[i];
		}
		return checkDeadlock2(A, C, available2, flags);
	}

	public static boolean[] checkDeadlock2(int[][] A, int[][] C,int[] available, boolean[] flags) {
		int[][] need = new int[A.length][A[0].length];
		int[] temp = new int[available.length];

		for (int i = 0; i < temp.length; i++) {
			temp[i] = available[i];
		}

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				need[i][j] = C[i][j] - A[i][j];
			}
		}

		for (int i = 0; i < A.length; i++) {
			if (flags[i] == false) {
				if (checkHelp(available, need[i])) {
					for (int k = 0; k < available.length; k++)
						available[k] += A[i][k];
					flags[i] = true;
				}
			}
		}
		if (Arrays.equals(temp, available)) {
			return flags;
		} else {
			return checkDeadlock(A, C, available, flags);
		}
	}

	public static void main(String[] args) {
		int[][] A, C, N;
		int[] resources;
		int Procces;
		int Resources;
		int[] Available;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of procces in the system: ");
		Procces = sc.nextInt();
		System.out.print("Enter the amount of wanted resources: ");
		Resources = sc.nextInt();
		Available = new int[Resources];
		resources = new int[Resources];
		N = new int[Procces][Resources];
		C = new int[Procces][Resources];
		A = new int[Procces][Resources];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				System.out.println("Enter matrix Allocation index: [" + i + "]"
						+ "[" + j + "]");
				A[i][j] = sc.nextInt();
			}
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				System.out.println("Enter matrix Claim index: [" + i + "]"
						+ "[" + j + "]");
				C[i][j] = sc.nextInt();
			}
		}
		for (int i = 0; i < Available.length; i++) {
			System.out.println("Enter matrix available index: [" + i + "]");
			Available[i] = sc.nextInt();
		}

		/*
		 * int sum=0; for(int i=0;i<Resources;i++){ sum=0; for(int
		 * j=0;j<Procces;j++){ sum+=C[j][i]; }
		 * System.out.println("Minimum resources from type R"
		 * +i+" requiered for safe running: "+sum); }
		 */
		
		for(int i=0;i<Resources;i++){
			for(int j=0;j<Procces;j++){
				resources[i]+=C[j][i];
			}
		}

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				N[i][j] = C[i][j] - A[i][j];
			}
		}

		boolean[] flags = new boolean[Procces];
		for (int i = 0; i < flags.length; i++)
			flags[i] = false;
		flags = checkDeadlock(A, C, Available, flags);
		boolean b = true;
		for (int i = 0; i < flags.length; i++) {
			if (flags[i] == false)
				b = false;
		}
		if (b) {
			System.out.println("the system is safe for use.");
			String reso;
			System.out
					.println("please enter wanted resource (between A to E): ");
			reso = sc.next();
			int place = reso.charAt(0) - 65;
			while (b && Available[place] != -1) {
				Available[place]--;
				for (int i = 0; i < flags.length; i++) {
					flags[i] = false;
				}
				flags = checkDeadlock(A, C, Available, flags);
				for (int i = 0; i < flags.length && b; i++) {
					if (flags[i] == false)
						b = false;
				}
			}
			System.out.println("minmal resource requierd: "+ (Available[place] + 1));
		} else {
			System.out.println("There is a deadlock with the procces:");
			for (int i = 0; i < flags.length; i++) {
				if (flags[i] == false) {
					System.out.println("P" + i);
				}
			}
			String reso;
			System.out.println("please enter wanted resource (between A to E): ");
			reso=sc.next();
			int place=reso.charAt(0)-65;
			while(!b && Available[place]!=resources[place]){
				b=true;
				Available[place]++;
				for(int i=0;i<flags.length;i++){
					flags[i]=false;
				}
				flags=checkDeadlock(A,C,Available,flags);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i] == false)
						b = false;
				}
			}
			if(Available[place]==resources[place]){
				System.out.println("Inevitable deadlock.");
			}
			else{
				System.out.println("minimal resource requierd: "+(Available[place]));
			}
		}

	}

}