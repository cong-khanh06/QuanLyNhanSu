/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import BUS.NhanVien_BUS;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author khanh
 */
public class NhanVien_DAO extends Connection_DAO{
    
    public NhanVien_DAO(){
        super();
    }
    public List<Object[]> layDanhSachNV(){
        List<Object[]> listResult=new ArrayList<>();
            
        try {
            String sqlNV="SELECT ma_nv, ho_ten, gioi_tinh, ngay_sinh, dia_chi, sdt, ma_pb, ma_cv, trang_thai FROM NhanVien";
            ResultSet rsNV=stmt.executeQuery(sqlNV);

            while(rsNV.next()){
                String maNV=rsNV.getString("ma_nv");
                String hoTen=rsNV.getString("ho_ten");
                String gioiTinh=rsNV.getString("gioi_tinh");
                String ngaySinh=rsNV.getString("ngay_sinh");
                String diaChi=rsNV.getString("dia_chi");
                String sdt=rsNV.getString("sdt");
                String maPB=rsNV.getString("ma_pb");
                String maCV=rsNV.getString("ma_cv");
                String trangThai=rsNV.getString("trang_thai");

                if(gioiTinh != null){
                    try {
                        NhanVien_BUS.GioiTinh gt= NhanVien_BUS.GioiTinh.valueOf(gioiTinh.trim());
                        gioiTinh= gt.getGioiTinhHienThi();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }

                if(trangThai != null){
                    try {
                        NhanVien_BUS.TrangThaiNhanVien ttnv=NhanVien_BUS.TrangThaiNhanVien.valueOf(trangThai.trim());
                        trangThai=ttnv.getTrangThaiHienThi();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                }

                   Object[] rowData={maNV,hoTen,gioiTinh,ngaySinh,diaChi,sdt,maPB,maCV,trangThai};
                   listResult.add(rowData);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return listResult;
        }
    
}
