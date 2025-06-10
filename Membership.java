import java.util.*;
import model.*;

public class Membership {
     private static final List<Member> daftarMember = new ArrayList<>(
        Arrays.asList(
            new Member("Hasbil", "AB1234", 50),
            new Member("Cantika", "CD5678", 25),
            new Member("Farel", "EF9012", 60)
        )
    );

    public Member tambahMember(String member) {
        Member newMember = new Member(member);
        daftarMember.add(newMember);
        return newMember;
    }

    public Member cariByKode(String kode) {
        for (Member m : daftarMember) {
            if (m.getKode().equalsIgnoreCase(kode)) {
                return m;
            }
        }
        return null;
    }
    
    public Member prosesMembership() {
        Scanner input = new Scanner(System.in);
        Member member = null;
        
        System.out.print("Apakah Anda memiliki kode member? (y/n): ");
        String jawaban = input.nextLine().trim();

        if (jawaban.equalsIgnoreCase("y")) {
            System.out.print("Masukkan kode member: ");
            String kode = input.nextLine().trim();
            member = cariByKode(kode);

            if (member == null) {
                System.out.println("Kode member tidak ditemukan.");
            } else {
                System.out.println("Halo member, " + member.getNama());
            }
        } else {
            System.out.print("Masukkan nama Anda: ");
            String nama = input.nextLine().trim();
            member = tambahMember(nama);
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Member berhasil dibuat dengan kode: " + member.getKode());
            System.out.println("----------------------------------------------------------------------------");
        }
        return member;
    }
}
