import os
import shutil
from PIL import Image

def main():
    # Menentukan path folder kerja
    folder_path = "gambar"
    source_filename = "image.jpg"
    
    # Path lengkap ke file sumber
    source_path = os.path.join(folder_path, source_filename)

    # Cek apakah file sumber ada
    if not os.path.exists(source_path):
        print(f"Error: File {source_filename} tidak ditemukan di dalam folder {folder_path}.")
        return

    try:
        # --- LANGKAH 1: Membuka Gambar ---
        with Image.open(source_path) as img:
            
            # --- LANGKAH 2: Mengubah Ukuran (Resize) menjadi 200x200 ---
            # Menggunakan ANTIALIAS (LANCZOS) agar hasil resize halus
            img_resized = img.resize((200, 200), Image.Resampling.LANCZOS)
            
            # --- LANGKAH 3: Simpan gambar yang sudah di-resize ---
            resized_filename = "resized_image.jpg"
            resized_path = os.path.join(folder_path, resized_filename)
            img_resized.save(resized_path)
            
            print(f"Gambar berhasil disimpan sebagai {resized_filename}")

        # --- LANGKAH 4 & 5: Membuat Salinan (Copy/Move) ---
        # Soal meminta membuat salinan ke file baru bernama copied_image.jpg
        copied_filename = "copied_image.jpg"
        copied_path = os.path.join(folder_path, copied_filename)
        
        # Menggunakan shutil.copy untuk menyalin file resized ke copied
        shutil.copy(resized_path, copied_path)
        print(f"Salinan gambar berhasil dibuat dengan nama {copied_filename}")

        # Catatan untuk Langkah 5: 
        # Soal menyebut "dipindahkan ke folder berbeda", namun Screenshot Output 
        # menunjukkan semua file tetap berada dalam folder 'gambar'.
        # Kode ini mengikuti Tampilan Screenshot agar Outputnya sama persis.

        # --- LANGKAH 6: Ubah Format dari JPG ke PNG ---
        # Buka file yang baru saja disalin
        with Image.open(copied_path) as img_copy:
            png_filename = "image_converted.png"
            png_path = os.path.join(folder_path, png_filename)
            
            # Simpan dengan format PNG
            img_copy.save(png_path, format="PNG")
            
            print(f"Format gambar berhasil diubah menjadi PNG dan disimpan dengan nama {png_filename}")

    except Exception as e:
        print(f"Terjadi kesalahan: {e}")

if __name__ == "__main__":
    main()