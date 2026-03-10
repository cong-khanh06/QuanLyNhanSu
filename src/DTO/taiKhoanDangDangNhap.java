/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author khanh
 */

import DTO.taiKhoanDangDangNhap;
public class taiKhoanDangDangNhap {
    public static TaiKhoan_DTO tkHienTai=null;

    public static TaiKhoan_DTO getTkHienTai() {
        return tkHienTai;
    }

    public static void setTkHienTai(TaiKhoan_DTO tkHienTai) {
        taiKhoanDangDangNhap.tkHienTai = tkHienTai;
    }
    
}
