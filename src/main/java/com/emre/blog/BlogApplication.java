package com.emre.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
/*
	 Blog Yönetim Sistemi
Amaç: Kullanıcıların blog yazıları yazıp, düzenleyip, silebildiği bir sistem oluşturmak.

Gereksinimler:
•    Kullanıcıların sisteme giriş yapabilmesi için kimlik doğrulama gereklidir.
•    Kullanıcılar blog yazıları yazabilmeli, düzenleyebilmeli ve silebilmelidir.
• 	 Her blog yazısı bir başlık, içerik, yazar ve yayınlanma tarihi içermelidir.
•    Blog yazılarının listelenmesi ve tarih veya yazar bazında filtrelenmesi sağlanmalıdır.
•    Kullanıcıların blog yazılarına yorum yapabilmesi sağlanmalıdır.

Veri Yapısı:
•    Blog Yazıları: Her blog yazısı aşağıdaki bilgileri içermelidir:
•    Başlık: Blog yazısının başlığı.
•    İçerik: Blog yazısının içeriği.
•    Yazar: Blog yazısının yazarı.
•    Yayınlanma Tarihi: Blog yazısının yayınlanma tarihi.
•       Yorumlar: Her yorum aşağıdaki bilgileri içermelidir:
•    Yazı ID: Yoruma yapılan blog yazısının kimliği (foreign key).
•    Yorum Yapan: Yorumu yapan kullanıcının adı veya kullanıcı kimliği.
*     Yorum İçeriği: Yorumun metni.
•    Yorum Tarihi: Yorumun yapıldığı tarih.



* İşlevler:
Blog Yazısı Ekleme:
 • Kullanıcılar yeni bir blog yazısı ekleyebilmelidir.
Blog Yazısı Düzenleme:
 • Kullanıcılar kendi blog yazılarını düzenleyebilmelidir.
Blog Yazısı Silme:
 • Kullanıcılar kendi blog yazılarını silebilmelidir.
Blog Yazılarının Listelenmesi ve Filtrelenmesi:
 • Tüm blog yazıları listelenebilir ve tarih veya yazar bazında filtrelenerek görüntülenebilir.
Yorum Yapma:
•    Kullanıcılar blog yazılarına yorum yapabilmelidir.
Kullanıcı Arayüzü:
Blog Yazısı Ekleme/Düzenleme/Silme Formu:
 • Kullanıcılar yeni blog yazısı ekleyebilir, mevcut yazılarını düzeltebilir ve silebilir.
Blog Yazıları Listesi ve Filtreleme Arayüzü:
 • Kullanıcılar tüm blog yazılarını görebilir ve tarih veya yazar bazında filtreleyebilir.
Yorum Yapma Formu:
•    Kullanıcılar blog yazılarına yorum yapmak için bu formu kullanır.

* Veritabanı Tasarımı:
•    Blog Yazıları Tablosu (BlogPosts):
•    Başlık (VARCHAR)
•    İçerik (TEXT)
•    Yazar (VARCHAR)
•    Yayınlanma Tarihi (DATETIME)

•    Yorumlar Tablosu (Comments):
•    Yazı ID (INT, Foreign Key)
•    Yorum Yapan (VARCHAR)
•    Yorum İçeriği (TEXT)
•    Yorum Tarihi (DATETIME)
*  */