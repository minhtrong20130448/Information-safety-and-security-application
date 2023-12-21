package symmetry;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

public class Hill {
	private final String ALPHABET = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆFGHIÍÌỈĨỊJKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVWXYÝỲỶỸỴZaáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệfghiíìỉĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz,. ";
	private String key;
	private RealMatrix keyMatrix;

	public Hill() {
	}

	public String getKey() {
		if (key == null)
			return null;
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RealMatrix getKeyMatrix() {
		return keyMatrix;
	}

	public void setKeyMatrix(RealMatrix keyMatrix) {
		this.keyMatrix = keyMatrix;
	}

	public void createKeyHill(int size) {
		RealMatrix key = generateValidKey(size);
		this.key = matrixKeyToString(key);
		this.keyMatrix = key;
	}

	public void enterKeyHill(String key, int sizeMatrix) {
		int keyLength = key.length();

		if (sizeMatrix == 2 && key.length() != 4) {
			throw new IllegalArgumentException("Nhập key từ 4 ký tự (ma trận 2x2)");
		}else if (sizeMatrix == 3 && key.length() != 9) {
			throw new IllegalArgumentException("Nhập key từ 9 ký tự (ma trận 2x2)");
		}else if(sizeMatrix == 4 && key.length() != 16){
			throw new IllegalArgumentException("Nhập key từ 16 ký tự (ma trận 2x2)");
		}else if(sizeMatrix == 5 && key.length() != 25){
			throw new IllegalArgumentException("Nhập key từ 25 ký tự (ma trận 2x2)");
		}else {
			this.key = key;
			RealMatrix keyMatrix = convertKeyToMatrix(key, sizeMatrix);
			this.keyMatrix = keyMatrix;
		}
	}
	

	public String encryptWithCommonsMath3(String plaintext, RealMatrix keyMatrix) {
		int blockSize = keyMatrix.getRowDimension();
		int length = plaintext.length();

		StringBuilder ciphertext = new StringBuilder();
		while (length % blockSize != 0) {
			plaintext += "Z";
			length++;
		}
		for (int i = 0; i < length; i += blockSize) {
			String block = plaintext.substring(i, i + blockSize);
			RealMatrix blockMatrix = createMatrixFromBlock(block);
			RealMatrix result = compensation(keyMatrix.multiply(blockMatrix));
			ciphertext.append(matrixToBlock(result));
		}

		return ciphertext.toString();
	}

	public String decryptWithCommonsMath3(String ciphertext, RealMatrix keyMatrix) {
		if (!isMatrixInvertible(keyMatrix))
			throw new IllegalArgumentException("The matrix is not invertible!");

		LUDecomposition luDecomposition = new LUDecomposition(keyMatrix);
		double detKey = luDecomposition.getDeterminant();

		RealMatrix trasposeMatrixKey = keyMatrix.transpose();
		RealMatrix pK = null;
		RealMatrix inverseMatrix = null;
		int sizeK = keyMatrix.getColumnDimension();
		if (sizeK == 2) {
			pK = calculateAdjugateMatrix(trasposeMatrixKey);
			int inverseDetK = calculateInverseZ26(detKey);
			inverseMatrix = compensation(pK.scalarMultiply(inverseDetK));
		} else if (sizeK > 2) {
			double mid = multiplicativeInverseDeterminant(keyMatrix);
			double valueInverse = calculateInverseZ26(mid);
			pK = compensation(calculateAdjugateMatrix(keyMatrix));
			inverseMatrix = compensation(pK.scalarMultiply(valueInverse));
		}
		int blockSize = inverseMatrix.getRowDimension();
		int length = ciphertext.length();

		StringBuilder plaintext = new StringBuilder();

		for (int i = 0; i < length; i += blockSize) {
			String block = ciphertext.substring(i, i + blockSize);
			RealMatrix blockMatrix = createMatrixFromBlock(block);
			RealMatrix result = compensation(inverseMatrix.multiply(blockMatrix));
			plaintext.append(matrixToBlock(result));
		}
		return plaintext.toString();
	}

	// Phương thức tính phần phụ đại số
	private RealMatrix calculateAdjugateMatrix(RealMatrix matrix) {
		int rows = matrix.getRowDimension();
		int cols = matrix.getColumnDimension();

		if (rows == 2 && cols == 2) {
			double a = matrix.getEntry(0, 0);
			double b = matrix.getEntry(1, 0);
			double c = matrix.getEntry(0, 1);
			double d = matrix.getEntry(1, 1);
			return MatrixUtils.createRealMatrix(new double[][] { { d, -b }, { -c, a } });
		} else {
			RealMatrix adjugateMatrix = MatrixUtils.createRealMatrix(rows, cols);

			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					RealMatrix minorMatrix = matrix.getSubMatrix(getMinorMatrixRows(j, cols),
							getMinorMatrixCols(i, rows));
					double minorDeterminant = new LUDecomposition(minorMatrix).getDeterminant();
					int index = i + j;
					double kofactor = Math.pow(-1, index) * minorDeterminant;
					adjugateMatrix.setEntry(i, j, kofactor);
				}
			}

			return adjugateMatrix;
		}
	}

	// gia tri nghich dao cua dinh thuc
	public double multiplicativeInverseDeterminant(RealMatrix realMatrix) {
		double result = 0;
		int lengthMatrix = realMatrix.getRowDimension();
		for (int i = 0; i < lengthMatrix; i++) {
			int value = (int) (realMatrix.getEntry(0, i));
			RealMatrix subMatrix = realMatrix.getSubMatrix(getMinorMatrixRows(0, lengthMatrix),
					getMinorMatrixCols(i, lengthMatrix));
			double det = new LUDecomposition(subMatrix).getDeterminant();
			result += Math.pow(-1, i) * (value * det);
		}

		if (result > ALPHABET.length()-1) {
			result = result % ALPHABET.length();
		} else if (result < 0) {
			while (result < 0) {
				result += ALPHABET.length();
			}
		}

		return result;
	}

	// Phương thức lấy chỉ số hàng cho ma trận con
	private int[] getMinorMatrixRows(int excludedRow, int totalRows) {
		int[] rows = new int[totalRows - 1];
		for (int i = 0, row = 0; i < totalRows; i++) {
			if (i != excludedRow) {
				rows[row++] = i;
			}
		}
		return rows;
	}

	// Phương thức lấy chỉ số cột cho ma trận con
	private int[] getMinorMatrixCols(int excludedCol, int totalCols) {
		int[] cols = new int[totalCols - 1];
		for (int i = 0, col = 0; i < totalCols; i++) {
			if (i != excludedCol) {
				cols[col++] = i;
			}
		}
		return cols;
	}

	private RealMatrix createMatrixFromBlock(String block) {
		double[][] matrixData = new double[block.length()][1];

		for (int i = 0; i < block.length(); i++) {
			matrixData[i][0] = ALPHABET.indexOf(block.charAt(i));
		}
		return new Array2DRowRealMatrix(matrixData);
	}

	private String matrixToBlock(RealMatrix matrix) {
		StringBuilder block = new StringBuilder();

		for (int i = 0; i < matrix.getRowDimension(); i++) {
			int value = (int) (matrix.getEntry(i, 0));
			if (value < 0) {
				while (value < 0) {
					value += ALPHABET.length();
				}
			} else if (value > ALPHABET.length()) {
				value = value % ALPHABET.length();
			}
			block.append(ALPHABET.charAt(value));
		}
		return block.toString();
	}

	// Tính toán giá trị nghịch đảo trên Z26
	private int calculateInverseZ26(double number) {
		for (int i = 1; i < ALPHABET.length(); i++) {
			if ((number * i) % ALPHABET.length() == 1) {
				return i;
			}
		}
		throw new IllegalArgumentException("The matrix is ​​not invertible MOD Z26!");
	}

	// Kiểm tra có giá trị nghịch đảo trên Z26
	private boolean isInverse26(double number) {
		for (int i = 1; i < ALPHABET.length(); i++) {
			if ((number * i) % ALPHABET.length() == 1) {
				return true;
			}
		}
		return false;
	}

	// Hàm tính UCLN
	public double calculateGCD(double detKey, double b) {
		while (b != 0) {
			double temp = b;
			b = detKey % b;
			detKey = temp;
		}
		return Math.abs(detKey); // Đảm bảo trả về giá trị không âm
	}

	// Chuyển ma trận sang vành Z26
	private RealMatrix compensation(RealMatrix matrix) {
		int numRows = matrix.getRowDimension();
		int numCols = matrix.getColumnDimension();

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				double value = matrix.getEntry(i, j);
				if (value < 0) {
					value = Math.round(value % ALPHABET.length());
					while (value < 0) {
						value += ALPHABET.length();
					}
					matrix.setEntry(i, j, value);
				} else if (value > ALPHABET.length()-1) {
					value = Math.round(value);
					matrix.setEntry(i, j, value % ALPHABET.length());
				}
			}
		}
		return matrix;
	}

	// Kiểm tra ma trận có phải là ma trận nghịch đảo hay không
	public boolean isMatrixInvertible(RealMatrix matrix) {
		// Lấy định thức của ma trận
		double determinant = new LUDecomposition(matrix).getDeterminant();

		// Kiểm tra xem định thức có khác 0 không
		return Math.abs(determinant) > 1e-10; // Sử dụng một ngưỡng nhỏ để xử lý sai số thấp
	}

	// hàm tạo ra key hợp lệ
	public RealMatrix generateValidKey(int size) {
		RealMatrix keyMatrix;

		do {
			// Tạo một ma trận ngẫu nhiên có kích thước là size x size
			double[][] randomArray = new double[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					randomArray[i][j] = (int) (Math.random() * 26);
				}
			}

			keyMatrix = MatrixUtils.createRealMatrix(randomArray);
		} while (!isValidKey(keyMatrix));

		return keyMatrix;
	}

	public boolean isValidKey(RealMatrix keyMatrix) {
		// Kiểm tra kích thước
		if (keyMatrix.getRowDimension() != keyMatrix.getColumnDimension()) {
			return false;
		}

		// Kiểm tra định thức khác 0
		double determinant = new LUDecomposition(keyMatrix).getDeterminant();
		if (Math.abs(determinant) == 0) {
			return false;
		}

		// Kiểm tra khả nghịch
		try {
			RealMatrix inverseMatrix = new LUDecomposition(keyMatrix).getSolver().getInverse();

		} catch (SingularMatrixException e) {
			return false;
		}

		if (!isInverse26(determinant)) {
			return false;
		}

		double mid = multiplicativeInverseDeterminant(keyMatrix);

		if (keyMatrix.getColumnDimension() > 2 && !isInverse26(mid)) {
			return false;
		}

		return true;
	}

	// Hàm kiểm tra xem ma trận K có khả nghịch không
	public boolean isMatrixInvertible(RealMatrix matrix, RealMatrix matrixInvetible) {
		int size = matrix.getRowDimension();
		RealMatrix identity = MatrixUtils.createRealIdentityMatrix(size);
		RealMatrix product = compensation(matrix.multiply(matrixInvetible));
		return product.equals(identity);
	}

	public RealMatrix convertKeyToMatrix(String key, int sizeMatrix) {
		key = key.toUpperCase();
		int keyLength = key.length();

		RealMatrix matrix = new Array2DRowRealMatrix(sizeMatrix, sizeMatrix);

		int row = 0, col = 0;
		System.out.println(row +", " + col);
		for (int i = 0; i < keyLength; i++) {
			int index = ALPHABET.indexOf(key.charAt(i));
			matrix.setEntry(row, col, index);
			System.out.println("row: " + row +", " + "col: " + col);
			col++;
			if (col == sizeMatrix) {
				col = 0;
				row++;
			}
			if(row == sizeMatrix) {
				row = 0;
			}
		}

		return matrix;
	}

	public String matrixKeyToString(RealMatrix matrix) {
		StringBuilder result = new StringBuilder();

		int rows = matrix.getRowDimension();
		int cols = matrix.getColumnDimension();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int index = (int) matrix.getEntry(i, j) % ALPHABET.length();
				char character = ALPHABET.charAt(index);
				result.append(character);
			}
		}

		return result.toString();
	}

	// Hàm in ma trận
	public String printMatrix(RealMatrix matrix) {
		StringBuilder result = new StringBuilder();

		int rows = matrix.getRowDimension();
		int cols = matrix.getColumnDimension();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.append(matrix.getEntry(i, j));
				if (j < cols - 1) {
					result.append(", ");
				}
			}
			if (i < rows - 1) {
				result.append("\n");
			}
		}
		return result.toString();
	}

	public String checkInsufficientData(String data) {
		if (data.length() % 2 != 0)
			return "data size insufficient, Z added";
		return "";
	}
	
	public boolean checkLanguageIsVI(String data) {
		String vietnameseCharacters = "ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";
		data = data.toUpperCase();
		for (char c : data.toCharArray()) {
	        if (vietnameseCharacters.contains(String.valueOf(c))) {
	            return true; 
	        }
	    }
		return false;
	}
}
