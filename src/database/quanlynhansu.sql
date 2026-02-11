-- DROP SCHEMA dbo;

CREATE DATABASE quanlynhansu;
GO

USE quanlynhansu;
GO

CREATE SCHEMA dbo;
-- quanlynhansu.dbo.BoPhan definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.BoPhan;

CREATE TABLE quanlynhansu.dbo.BoPhan (
	ma_bp varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_bp nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT PK__BoPhan__0FE17EFDEC2D9814 PRIMARY KEY (ma_bp)
);


-- quanlynhansu.dbo.DuAn definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.DuAn;

CREATE TABLE quanlynhansu.dbo.DuAn (
	ma_da varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_du_an nvarchar(150) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_bat_dau date NULL,
	ngay_ket_thuc date NULL,
	trang_thai nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__DuAn__0FE14F0E39BA0164 PRIMARY KEY (ma_da)
);


-- quanlynhansu.dbo.LoaiBaoHiem definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.LoaiBaoHiem;

CREATE TABLE quanlynhansu.dbo.LoaiBaoHiem (
	ma_bh varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_bh nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__LoaiBaoH__0FE17EC58882FF30 PRIMARY KEY (ma_bh)
);


-- quanlynhansu.dbo.LoaiDon definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.LoaiDon;

CREATE TABLE quanlynhansu.dbo.LoaiDon (
	ma_loai_don varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_loai_don nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__LoaiDon__D9D2B98F16C245B6 PRIMARY KEY (ma_loai_don)
);


-- quanlynhansu.dbo.PhuCap definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.PhuCap;

CREATE TABLE quanlynhansu.dbo.PhuCap (
	ma_pc varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_pc nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	so_tien decimal(15,2) NULL,
	CONSTRAINT PK__PhuCap__0FE0AF8DCE254AA2 PRIMARY KEY (ma_pc)
);


-- quanlynhansu.dbo.ChucVu definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.ChucVu;

CREATE TABLE quanlynhansu.dbo.ChucVu (
	ma_cv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_cv nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	mo_ta nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_pc varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ChucVu__0FE17696662F4FC4 PRIMARY KEY (ma_cv),
	CONSTRAINT FK__ChucVu__ma_pc__3E52440B FOREIGN KEY (ma_pc) REFERENCES quanlynhansu.dbo.PhuCap(ma_pc)
);


-- quanlynhansu.dbo.PhongBan definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.PhongBan;

CREATE TABLE quanlynhansu.dbo.PhongBan (
	ma_pb varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_pb nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	dia_chi nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sdt_phong varchar(15) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_bp varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__PhongBan__0FE0AF82DCFE0781 PRIMARY KEY (ma_pb),
	CONSTRAINT FK__PhongBan__ma_bp__398D8EEE FOREIGN KEY (ma_bp) REFERENCES quanlynhansu.dbo.BoPhan(ma_bp)
);


-- quanlynhansu.dbo.NhanVien definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.NhanVien;

CREATE TABLE quanlynhansu.dbo.NhanVien (
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ho_ten nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ngay_sinh date NULL,
	gioi_tinh nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	dia_chi nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sdt varchar(15) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	email varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cccd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_vao_lam date NULL,
	trang_thai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT N'DangLam' NULL,
	ma_pb varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_cv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__NhanVien__0FE15F7C2B00B1E0 PRIMARY KEY (ma_nv),
	CONSTRAINT UQ__NhanVien__37D42BFA55736D49 UNIQUE (cccd),
	CONSTRAINT UQ__NhanVien__AB6E6164E17D232E UNIQUE (email),
	CONSTRAINT FK__NhanVien__ma_cv__44FF419A FOREIGN KEY (ma_cv) REFERENCES quanlynhansu.dbo.ChucVu(ma_cv),
	CONSTRAINT FK__NhanVien__ma_pb__440B1D61 FOREIGN KEY (ma_pb) REFERENCES quanlynhansu.dbo.PhongBan(ma_pb)
);
ALTER TABLE quanlynhansu.dbo.NhanVien WITH NOCHECK ADD CONSTRAINT CHK_GioiTinh CHECK (([gioi_tinh]=N'Khac' OR [gioi_tinh]=N'Nu' OR [gioi_tinh]=N'Nam'));
ALTER TABLE quanlynhansu.dbo.NhanVien WITH NOCHECK ADD CONSTRAINT CHK_TrangThaiNV CHECK (([trang_thai]=N'TamNghi' OR [trang_thai]=N'NghiViec' OR [trang_thai]=N'DangLam'));


-- quanlynhansu.dbo.PhanCongDuAn definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.PhanCongDuAn;

CREATE TABLE quanlynhansu.dbo.PhanCongDuAn (
	ma_da varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	nguoi_quan_ly varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	phong_quan_ly varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	vai_tro nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_tham_gia date DEFAULT getdate() NULL,
	ngay_ket_thuc date NULL,
	CONSTRAINT PK_PhanCongDuAn PRIMARY KEY (ma_da,ma_nv),
	CONSTRAINT FK_PCDA_DuAn FOREIGN KEY (ma_da) REFERENCES quanlynhansu.dbo.DuAn(ma_da),
	CONSTRAINT FK_PCDA_NguoiQuanLy FOREIGN KEY (nguoi_quan_ly) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv),
	CONSTRAINT FK_PCDA_NguoiThamGia FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv),
	CONSTRAINT FK_PCDA_PhongBan FOREIGN KEY (phong_quan_ly) REFERENCES quanlynhansu.dbo.PhongBan(ma_pb)
);


-- quanlynhansu.dbo.TaiKhoan definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.TaiKhoan;

CREATE TABLE quanlynhansu.dbo.TaiKhoan (
	ma_tk varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_dang_nhap varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	mat_khau varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	quyen_truy_cap varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__TaiKhoan__0FE0CD14D4A741E7 PRIMARY KEY (ma_tk),
	CONSTRAINT UQ__TaiKhoan__0FE15F7DD0B74EE5 UNIQUE (ma_nv),
	CONSTRAINT UQ__TaiKhoan__363698B34FAB1FB6 UNIQUE (ten_dang_nhap),
	CONSTRAINT FK__TaiKhoan__ma_nv__4BAC3F29 FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);
ALTER TABLE quanlynhansu.dbo.TaiKhoan WITH NOCHECK ADD CONSTRAINT CHK_Quyen CHECK (([quyen_truy_cap]='Manager' OR [quyen_truy_cap]='User' OR [quyen_truy_cap]='Admin'));


-- quanlynhansu.dbo.BangCap definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.BangCap;

CREATE TABLE quanlynhansu.dbo.BangCap (
	ma_bc varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten_bc nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chuyen_nganh nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	noi_cap nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_cap date NULL,
	xep_loai nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__BangCap__0FE17EC2D51CA74E PRIMARY KEY (ma_bc),
	CONSTRAINT FK__BangCap__ma_nv__4F7CD00D FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);


-- quanlynhansu.dbo.BangChamCong definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.BangChamCong;

CREATE TABLE quanlynhansu.dbo.BangChamCong (
	ma_cc varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ngay_tao date DEFAULT getdate() NULL,
	gio_vao time NULL,
	gio_ra time NULL,
	so_gio_tang_ca float DEFAULT 0 NULL,
	trang_thai nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__BangCham__0FE176E3D8F43332 PRIMARY KEY (ma_cc),
	CONSTRAINT FK__BangChamC__ma_nv__74AE54BC FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);


-- quanlynhansu.dbo.BangLuong definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.BangLuong;

CREATE TABLE quanlynhansu.dbo.BangLuong (
	ma_bl varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	NgayTao date DEFAULT getdate() NULL,
	luong_co_ban decimal(15,2) NULL,
	tong_phu_cap decimal(15,2) NULL,
	tong_khen_thuong decimal(15,2) NULL,
	tong_khau_tru decimal(15,2) NULL,
	thuc_lanh decimal(15,2) NULL,
	ngay_tinh_luong date DEFAULT getdate() NULL,
	trang_thai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__BangLuon__0FE17EF94763CEC8 PRIMARY KEY (ma_bl),
	CONSTRAINT FK__BangLuong__ma_nv__797309D9 FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);
ALTER TABLE quanlynhansu.dbo.BangLuong WITH NOCHECK ADD CONSTRAINT CHK_TrangThaiLuong CHECK (([trang_thai]='DaThanhToan' OR [trang_thai]='ChuaThanhToan'));


-- quanlynhansu.dbo.ChiTietBaoHiem definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.ChiTietBaoHiem;

CREATE TABLE quanlynhansu.dbo.ChiTietBaoHiem (
	ma_ctbh varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	so_the_bh varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_cap date NULL,
	noi_cap nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ty_le_nv_dong float NULL,
	ty_le_cty_dong float NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_bh varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ChiTietB__5AE48C431966B3F0 PRIMARY KEY (ma_ctbh),
	CONSTRAINT FK__ChiTietBa__ma_bh__59063A47 FOREIGN KEY (ma_bh) REFERENCES quanlynhansu.dbo.LoaiBaoHiem(ma_bh),
	CONSTRAINT FK__ChiTietBa__ma_nv__5812160E FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);


-- quanlynhansu.dbo.ChiTietDon definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.ChiTietDon;

CREATE TABLE quanlynhansu.dbo.ChiTietDon (
	ma_don varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ngay_tao date DEFAULT getdate() NULL,
	noi_dung nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_bat_dau_nghi date NULL,
	ngay_ket_thuc_nghi date NULL,
	trang_thai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT 'ChoDuyet' NULL,
	nguoi_duyet_id varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_loai_don varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ChiTietD__057D6CE1DA5B5587 PRIMARY KEY (ma_don),
	CONSTRAINT FK__ChiTietDo__ma_lo__6754599E FOREIGN KEY (ma_loai_don) REFERENCES quanlynhansu.dbo.LoaiDon(ma_loai_don),
	CONSTRAINT FK__ChiTietDo__ma_nv__656C112C FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv),
	CONSTRAINT FK__ChiTietDo__nguoi__66603565 FOREIGN KEY (nguoi_duyet_id) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);
ALTER TABLE quanlynhansu.dbo.ChiTietDon WITH NOCHECK ADD CONSTRAINT CHK_TrangThaiDon CHECK (([trang_thai]='TuChoi' OR [trang_thai]='DaDuyet' OR [trang_thai]='ChoDuyet'));


-- quanlynhansu.dbo.HopDong definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.HopDong;

CREATE TABLE quanlynhansu.dbo.HopDong (
	ma_hd varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	loai_hop_dong nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_bat_dau date NULL,
	ngay_ket_thuc date NULL,
	ngay_ky date NULL,
	muc_luong_co_ban decimal(15,2) NULL,
	noi_dung nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	trang_thai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ma_nv varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Lan_ky int NULL,
	ngay_len_luong_gan_nhat date NULL,
	CONSTRAINT PK__HopDong__0FE16E863E629316 PRIMARY KEY (ma_hd),
	CONSTRAINT FK__HopDong__ma_nv__52593CB8 FOREIGN KEY (ma_nv) REFERENCES quanlynhansu.dbo.NhanVien(ma_nv)
);
ALTER TABLE quanlynhansu.dbo.HopDong WITH NOCHECK ADD CONSTRAINT CHK_TrangThaiHD CHECK (([trang_thai]='Huy' OR [trang_thai]='HetHan' OR [trang_thai]='HieuLuc'));


-- quanlynhansu.dbo.KhenThuongKyLuat definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.KhenThuongKyLuat;

CREATE TABLE quanlynhansu.dbo.KhenThuongKyLuat (
	ma_ktkl varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ma_bl varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	loai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ngay_quyet_dinh date NULL,
	so_tien decimal(15,2) NULL,
	ly_do nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__KhenThuo__A84EA71D05ADE1F1 PRIMARY KEY (ma_ktkl),
	CONSTRAINT FK_KTKL_BangLuong FOREIGN KEY (ma_bl) REFERENCES quanlynhansu.dbo.BangLuong(ma_bl)
);
ALTER TABLE quanlynhansu.dbo.KhenThuongKyLuat WITH NOCHECK ADD CONSTRAINT CHK_LoaiKTKL CHECK (([loai]='KyLuat' OR [loai]='KhenThuong'));


-- quanlynhansu.dbo.UngLuong definition

-- Drop table

-- DROP TABLE quanlynhansu.dbo.UngLuong;

CREATE TABLE quanlynhansu.dbo.UngLuong (
	ma_ul varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ma_bl varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ngay_ung date NULL,
	so_tien decimal(15,2) NULL,
	ly_do nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	trang_thai nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT 'ChoDuyet' NULL,
	CONSTRAINT PK__UngLuong__0FE0C53651CE773D PRIMARY KEY (ma_ul),
	CONSTRAINT FK_UngLuong_BangLuong FOREIGN KEY (ma_bl) REFERENCES quanlynhansu.dbo.BangLuong(ma_bl)
);
ALTER TABLE quanlynhansu.dbo.UngLuong WITH NOCHECK ADD CONSTRAINT CHK_TrangThaiUL CHECK (([trang_thai]='TuChoi' OR [trang_thai]='DaDuyet' OR [trang_thai]='ChoDuyet'));