package com.rahmi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int[] pesanan = new int[5];
    static String[] menu = {"Nasi Goreng ", "Mie Goreng  ", "Nasi Ayam   ", "Es Teh Manis", "Es Jeruk    "};
    static int[] harga = {15000, 13000, 18000, 3000, 5000};

    public static void main(String[] args) {
        while (true) {
            tampilkanMenu();
            int pilihan = scanner.nextInt();
            switch (pilihan) {
                case 0:
                    System.exit(0);
                case 99:
                    konfirmasiPesanan();
                    break;
                default:
                    if (pilihan > 0 && pilihan <= menu.length) {
                        pesanMakanan(pilihan - 1);
                    } else {
                        System.out.println("\nPilihan tidak valid. Silakan masukkan nomor yang ada di menu.");
                    }
                    break;
            }
        }
    }

    static void tampilkanMenu() {
        System.out.println("\n============================");
        System.out.println(" Selamat datang di BinarFud ");
        System.out.println("============================\n");
        System.out.println("Silahkan pilih makanan:");

        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i] + "\t\t| " + harga[i]);
        }

        System.out.println("----------------------------");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0.  Keluar aplikasi\n");
        System.out.print("=> ");
    }

    static void pesanMakanan(int index) {
        System.out.println("\n============================");
        System.out.println("     Berapa pesanan anda      ");
        System.out.println("============================\n");

        System.out.println(menu[index] + "\t| " + harga[index]);

        System.out.println("(input 0 untuk kembali)\n");
        System.out.print("qty => ");

        int qty = scanner.nextInt();
        pesanan[index] += qty;
    }

    static void konfirmasiPesanan() {
        System.out.println("\n==============================");
        System.out.println("    Konfirmasi & Pembayaran     ");
        System.out.println("==============================\n");

        int total = 0;
        int jumlah = 0;
        for (int i = 0; i < pesanan.length; i++) {
            if (pesanan[i] > 0) {
                int subtotal = pesanan[i] * harga[i];
                System.out.println(menu[i] + "\t" + pesanan[i] + "\t\t" + subtotal);
                total += subtotal;
                jumlah += pesanan[i];
            }
        }
        System.out.println("------------------------------+");

        System.out.println("Total"+ "\t\t\t" + jumlah + "\t\t" + total + "\n");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi\n");
        System.out.print("=> ");

        int pilihan = scanner.nextInt();
        if (pilihan == 1) {
            cetakStruk(total, jumlah);
            pesanan = new int[5];
        } else if (pilihan == 0){
            System.exit(0);
        }
    }

    static void cetakStruk(int total, int jumlah) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        System.out.println("\n===============================");
        System.out.println("            BinarFud            ");
        System.out.println("===============================\n");
        System.out.println(formattedTime);
        System.out.println("Terima kasih sudah memesan di\nBinarFud\n");
        System.out.println("Dibawah ini adalah pesanan anda\n");

        for (int i = 0; i < pesanan.length; i++) {
            if (pesanan[i] > 0) {
                int subtotal = pesanan[i] * harga[i];
                System.out.println(menu[i] + "\t" + pesanan[i] + "\t\t" + subtotal);
            }
        }
        System.out.println("------------------------------+");

        System.out.println("Total"+ "\t\t\t" + jumlah + "\t\t" + total + "\n");
        System.out.println("Pembayaran : BinarCash\n");
        System.out.println("===============================");
        System.out.println("Simpan struk ini sebagai bukti\npembayaran");
        System.out.println("===============================\n");

        try {
            BufferedWriter writer = simpanStruk(total, jumlah, formattedTime);
            writer.close();
            System.out.println("Struk pembayaran telah disimpan dalam file struk_pembayaran.txt");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk pembayaran.");
        }
    }

    private static BufferedWriter simpanStruk(int total, int jumlah, String formattedTime) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("struk_pembayaran.txt"));
        writer.write("===============================\n");
        writer.write("            BinarFud            \n");
        writer.write("===============================\n");
        writer.write("\n");
        writer.write(formattedTime + "\n");
        writer.write("Terima kasih sudah memesan di\nBinarFud\n");
        writer.write("\n");
        writer.write("Dibawah ini adalah pesanan anda\n");
        writer.write("\n");

        for (int i = 0; i < pesanan.length; i++) {
            if (pesanan[i] > 0) {
                int subtotal = pesanan[i] * harga[i];
                writer.write(menu[i] + "\t" + pesanan[i] + "\t" + subtotal + "\n");
            }
        }
        writer.write("------------------------------+\n");

        writer.write("Total"+ "\t\t" + jumlah + "\t" + total + "\n");
        writer.write("\n");
        writer.write("Pembayaran : BinarCash\n");
        writer.write("\n");
        writer.write("===============================\n");
        writer.write("Simpan struk ini sebagai bukti\npembayaran\n");
        writer.write("===============================\n");
        return writer;
    }

}