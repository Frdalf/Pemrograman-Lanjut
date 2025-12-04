import json
import os

# Nama file JSON untuk menyimpan data buku
FILENAME = 'books.json'

def load_books():
    """Membaca data buku dari file JSON."""
    if not os.path.exists(FILENAME):
        return []
    try:
        with open(FILENAME, 'r') as file:
            return json.load(file)
    except (json.JSONDecodeError, ValueError):
        return []

def save_books(books):
    """Menyimpan data buku ke file JSON."""
    with open(FILENAME, 'w') as file:
        json.dump(books, file, indent=4)

def add_book():
    """1. Menambahkan buku baru."""
    print("\nMenambahkan Buku Baru")
    title = input("Masukkan judul buku: ")
    author = input("Masukkan pengarang buku: ")
    year = input("Masukkan tahun terbit buku: ")

    books = load_books()
    new_book = {
        "title": title,
        "author": author,
        "year": year
    }
    books.append(new_book)
    save_books(books)
    print("Buku berhasil ditambahkan.")

def view_books():
    """2. Menampilkan daftar buku."""
    books = load_books()
    print("\nDaftar Buku:")
    if not books:
        print("Belum ada buku yang tersimpan.")
    else:
        for index, book in enumerate(books, start=1):
            print(f"{index}. {book['title']} oleh {book['author']} ({book['year']})")
    return books

def update_book():
    """3. Mengupdate data buku."""
    books = view_books()
    if not books:
        return

    try:
        choice = int(input("\nMasukkan nomor buku yang ingin diupdate: "))
        if 1 <= choice <= len(books):
            selected_book = books[choice - 1]
            
            print("\nUpdate Buku:")
            new_title = input(f"Masukkan judul baru (kosongkan untuk mempertahankan '{selected_book['title']}'): ")
            new_author = input(f"Masukkan pengarang baru (kosongkan untuk mempertahankan '{selected_book['author']}'): ")
            new_year = input(f"Masukkan tahun terbit baru (kosongkan untuk mempertahankan '{selected_book['year']}'): ")

            if new_title:
                selected_book['title'] = new_title
            if new_author:
                selected_book['author'] = new_author
            if new_year:
                selected_book['year'] = new_year

            save_books(books)
            print("Buku berhasil diupdate.")
        else:
            print("Nomor buku tidak valid.")
    except ValueError:
        print("Input harus berupa angka.")

def delete_book():
    """4. Menghapus buku."""
    books = view_books()
    if not books:
        return

    try:
        choice = int(input("\nMasukkan nomor buku yang ingin dihapus: "))
        if 1 <= choice <= len(books):
            removed_book = books.pop(choice - 1)
            save_books(books)
            print("Buku berhasil dihapus.")
        else:
            print("Nomor buku tidak valid.")
    except ValueError:
        print("Input harus berupa angka.")

def main():
    while True:
        print("\n--- Menu Perpustakaan ---")
        print("1. Menambahkan Buku")
        print("2. Menampilkan Buku")
        print("3. Mengupdate Buku")
        print("4. Menghapus Buku")
        print("5. Keluar")
        
        choice = input("Pilih menu (1/2/3/4/5): ")

        if choice == '1':
            add_book()
        elif choice == '2':
            view_books()
        elif choice == '3':
            update_book()
        elif choice == '4':
            delete_book()
        elif choice == '5':
            print("Terima kasih!")
            break
        else:
            print("Pilihan tidak valid, silakan coba lagi.")

if __name__ == "__main__":
    main()