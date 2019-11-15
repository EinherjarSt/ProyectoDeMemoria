package model.epanet.jna;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import model.epanet.EpanetErrors;
import model.epanet.EpanetException;
import model.epanet.jna.IEpanetNative.pviewprog;

/**
 * 
 * Take and modified from https://github.com/TheHortonMachine/hortonmachine
 * 
 * JGrass - Free Open Source Java GIS http://www.jgrass.org (C) HydroloGIS -
 * www.hydrologis.com
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Library General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
public class EpanetDLL {
	private static IEpanetNative epanet;
	private String warningMessage;

	/**
	 * 
	 * @param lib     The name of library
	 * @param libPath Path where the library is located.
	 * @throws Exception exception when the library can't be loaded.
	 */
	public EpanetDLL(String lib, String libPath) throws Exception {
		if (epanet == null) {
			try {
				if (libPath != null)
					NativeLibrary.addSearchPath(lib, libPath);
				epanet = (IEpanetNative) Native.load(lib, IEpanetNative.class);
			} catch (Exception e) {
				throw new Exception("The library can't be loaded", e);
			}
		}
	}

	/**
	 * 
	 * Example:<br>
	 * <br>
	 * <code> IEpanetNative.pviewprog callback = new IEpanetNative.pviewprog() {<br>
	 * <br>
	 * 
	 * &nbsp; @Override <br>
	 * &nbsp; public void invoke(String text) { System.out.println(text); } <br>
	 * <br>
	 * }; <code>
	 * 
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param callback
	 * @return
	 * @see model.epanet.jna.IEpanetNative#ENepanet(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      model.epanet.jna.IEpanetNative.pviewprog)
	 */
	public int ENepanet(String f1, String f2, String f3, pviewprog callback) {
		return epanet.ENepanet(f1, f2, f3, callback);
	}

	/**
	 * 
	 * @param inputFile
	 * @param reportFile
	 * @param binaryOutputFile
	 * @throws EpanetException
	 */
	public void ENopen(String inputFile, String reportFile, String binaryOutputFile) throws EpanetException {
		int err = epanet.ENopen(inputFile, reportFile, binaryOutputFile);
		checkError(err);
	}

	/**
	 * 
	 * @param fname
	 * @throws EpanetException
	 */
	public void ENsaveinpfile(String fname) throws EpanetException {
		int err = epanet.ENsaveinpfile(fname);
		checkError(err);

	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENclose() throws EpanetException {
		int err = epanet.ENclose();
		checkError(err);

	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENsolveH() throws EpanetException {
		int err = epanet.ENsolveH();
		checkError(err);

	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENsaveH() throws EpanetException {
		int err = epanet.ENsaveH();
		checkError(err);

	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENopenH() throws EpanetException {
		int err = epanet.ENopenH();
		checkError(err);

	}

	/**
	 * 
	 * @param saveFlag
	 * @throws EpanetException
	 */
	public void ENinitH(int saveFlag) throws EpanetException {
		int err = epanet.ENinitH(saveFlag);
		checkError(err);
	}

	/**
	 * 
	 * @param t
	 * @throws EpanetException
	 */
	public void ENrunH(long[] t) throws EpanetException {
		int err = epanet.ENrunH(t);
		checkError(err);
	}

	/**
	 * 
	 * @param tstep
	 * @throws EpanetException
	 */
	public void ENnextH(long[] tstep) throws EpanetException {
		int err = epanet.ENnextH(tstep);
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENcloseH() throws EpanetException {
		int err = epanet.ENcloseH();
		checkError(err);
	}

	/**
	 * 
	 * @param fname
	 * @throws EpanetException
	 */
	public void ENsavehydfile(String fname) throws EpanetException {
		int err = epanet.ENsavehydfile(fname);
		checkError(err);
	}

	/**
	 * 
	 * @param fname
	 * @throws EpanetException
	 */
	public void ENusehydfile(String fname) throws EpanetException {
		int err = epanet.ENusehydfile(fname);
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENsolveQ() throws EpanetException {
		int err = epanet.ENsolveQ();
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENopenQ() throws EpanetException {
		int err = epanet.ENopenQ();
		checkError(err);
	}

	/**
	 * 
	 * @param saveflag
	 * @throws EpanetException
	 */
	public void ENinitQ(int saveflag) throws EpanetException {
		int err = epanet.ENinitQ(saveflag);
		checkError(err);
	}

	/**
	 * 
	 * @param t
	 * @throws EpanetException
	 */
	public void ENrunQ(Long t) throws EpanetException {
		int err = epanet.ENrunQ(t);
		checkError(err);
	}

	/**
	 * 
	 * @param tstep
	 * @throws EpanetException
	 */
	public void ENnextQ(Long tstep) throws EpanetException {
		int err = epanet.ENnextQ(tstep);
		checkError(err);
	}

	/**
	 * 
	 * @param tleft
	 * @throws EpanetException
	 */
	public void ENstepQ(Long tleft) throws EpanetException {
		int err = epanet.ENstepQ(tleft);
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENcloseQ() throws EpanetException {
		int err = epanet.ENcloseQ();
		checkError(err);
	}

	/**
	 * 
	 * @param line
	 * @throws EpanetException
	 */
	public void ENwriteline(String line) throws EpanetException {
		ByteBuffer buffer = EpanetUtils.stringToByteBuffer(line);

		int err = epanet.ENwriteline(buffer);
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENreport() throws EpanetException {
		int err = epanet.ENreport();
		checkError(err);
	}

	/**
	 * 
	 * @throws EpanetException
	 */
	public void ENresetreport() throws EpanetException {
		int err = epanet.ENresetreport();
		checkError(err);
	}

	public void ENsetreport(String command) throws EpanetException {
		int err = epanet.ENsetreport(command);
		checkError(err);
	}

	/**
	 * 
	 * @param cindex
	 * @return
	 * @throws EpanetException
	 */
	public EpanetControlResult ENgetcontrol(int cindex) throws EpanetException {
		IntBuffer ctypeBuffer = IntBuffer.allocate(1);
		IntBuffer lindexBuffer = IntBuffer.allocate(1);
		FloatBuffer settingBuffer = FloatBuffer.allocate(1);
		IntBuffer nindexBuffer = IntBuffer.allocate(1);
		FloatBuffer levelBuffer = FloatBuffer.allocate(1);
		int err = epanet.ENgetcontrol(cindex, ctypeBuffer, lindexBuffer, settingBuffer, nindexBuffer, levelBuffer);
		checkError(err);

		int ctype = EpanetUtils.IntBufferToInt(ctypeBuffer);
		int lindex = EpanetUtils.IntBufferToInt(lindexBuffer);
		float setting = EpanetUtils.FloatBufferToFloat(settingBuffer);
		int nindex = EpanetUtils.IntBufferToInt(nindexBuffer);
		float level = EpanetUtils.FloatBufferToFloat(levelBuffer);
		return new EpanetControlResult(cindex, ctype, lindex, setting, nindex, level);
	}

	/**
	 * 
	 * @param countcode
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetcount(EpanetCountComponentCode countcode) throws EpanetException {
		int[] count = new int[1];
		int err = epanet.ENgetcount(countcode.getCode(), count);
		checkError(err);
		return count[0];
	}

	/**
	 * 
	 * @param option
	 * @return
	 * @throws EpanetException
	 */
	public float ENgetoption(EpanetOptionCodes option) throws EpanetException {
		float[] value = new float[1];
		int err = epanet.ENgetoption(option.getCode(), value);
		checkError(err);
		return value[0];
	}

	/**
	 * 
	 * @param parameter
	 * @return
	 * @throws EpanetException
	 */
	public long ENgettimeparam(EpanetTimeParameterCodes parameter) throws EpanetException {
		long[] timevalue = new long[1];
		int err = epanet.ENgettimeparam(parameter.getCode(), timevalue);
		checkError(err);
		return timevalue[0];
	}

	/**
	 * 
	 * @return
	 * @throws EpanetException
	 */
	public EpanetFlowUnits ENgetflowunits() throws EpanetException {
		IntBuffer unitscode = IntBuffer.allocate(1);
		int err = epanet.ENgetflowunits(unitscode);
		checkError(err);
		int unitcode = EpanetUtils.IntBufferToInt(unitscode);
		return EpanetFlowUnits.convert(unitcode);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetpatternindex(String id) throws EpanetException {
		int[] index = new int[1];
		int err = epanet.ENgetpatternindex(id, index);
		checkError(err);
		return index[0];
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws EpanetException
	 */
	public String ENgetpatternid(int index) throws EpanetException {
		ByteBuffer id = ByteBuffer.allocate(32);
		int err = epanet.ENgetpatternid(index, id);
		checkError(err);
		return EpanetUtils.byteBufferToString(id);
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetpatternlen(int index) throws EpanetException {
		int[] len = new int[1];
		int err = epanet.ENgetpatternlen(index, len);
		checkError(err);
		return len[0];
	}

	/**
	 * 
	 * @param index
	 * @param period
	 * @return
	 * @throws EpanetException
	 */
	public float ENgetpatternvalue(int index, int period) throws EpanetException {
		float[] value = new float[1];
		int err = epanet.ENgetpatternvalue(index, period, value);
		checkError(err);
		return value[0];
	}

	/**
	 * 
	 * @param qualcode
	 * @param tracenode
	 * @return
	 * @throws EpanetException
	 */
	public int[] ENgetqualtype() throws EpanetException {
		IntBuffer qualcode = IntBuffer.allocate(1);
		IntBuffer tracenode = IntBuffer.allocate(1);
		int err = epanet.ENgetqualtype(qualcode, tracenode);
		checkError(err);

		return new int[] { EpanetUtils.IntBufferToInt(qualcode), EpanetUtils.IntBufferToInt(tracenode) };
	}

	/**
	 * 
	 * @param errcode
	 * @param nchar
	 * @return
	 * @throws EpanetException
	 */
	public String ENgeterror(int errcode, int nchar) throws EpanetException {
		ByteBuffer errmsg = ByteBuffer.allocate(80);
		int err = epanet.ENgeterror(errcode, errmsg, nchar);
		checkError(err);
		return EpanetUtils.byteBufferToString(errmsg);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetnodeindex(String id) throws EpanetException {
		int[] index = new int[1];
		int err = epanet.ENgetnodeindex(id, index);
		checkError(err);
		return index[0];
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws EpanetException
	 */
	public String ENgetnodeid(int index) throws EpanetException {
		ByteBuffer id = ByteBuffer.allocate(32);
		int err = epanet.ENgetnodeid(index, id);
		checkError(err);
		return EpanetUtils.byteBufferToString(id);
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws EpanetException
	 */
	public EpanetNodeType ENgetnodetype(int index) throws EpanetException {
		int[] typecode = new int[1];
		int err = epanet.ENgetnodetype(index, typecode);
		checkError(err);
		return EpanetNodeType.convert(typecode[0]);
	}

	/**
	 * 
	 * @param index
	 * @param param
	 * @return
	 * @throws EpanetException
	 */
	public float ENgetnodevalue(int index, EpanetNodeParameter param) throws EpanetException {
		float[] value = new float[1];
		int err = epanet.ENgetnodevalue(index, param.getCode(), value);
		checkError(err);
		return value[0];
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetlinkindex(String id) throws EpanetException {
		int[] index = new int[1];
		int err = epanet.ENgetlinkindex(id, index);
		checkError(err);
		return index[0];
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @throws EpanetException
	 */
	public String ENgetlinkid(int index) throws EpanetException {
		ByteBuffer id = ByteBuffer.allocate(32);
		int err = epanet.ENgetlinkid(index, id);
		checkError(err);

		return EpanetUtils.byteBufferToString(id);
	}

	/**
	 * 
	 * @param link index
	 * @return EpanetLinkType
	 * @throws EpanetException
	 */
	public EpanetLinkType ENgetlinktype(int index) throws EpanetException {
		int[] typecode = new int[1];
		int err = epanet.ENgetlinktype(index, typecode);
		checkError(err);
		return EpanetLinkType.convert(typecode[0]);
	}

	/**
	 * Obtains the index of the nodes at both ends of the edge
	 * 
	 * @param index index of link
	 * @return A array of size 2 that contain both ends of the edge s * @throws
	 *         EpanetException
	 */
	public int[] ENgetlinknodes(int index) throws EpanetException {
		int[] fromnode = new int[1];
		int[] tonode = new int[1];
		int err = epanet.ENgetlinknodes(index, fromnode, tonode);
		checkError(err);
		return new int[] { fromnode[0], tonode[0] };
	}

	/**
	 * 
	 * @param index
	 * @param paramcode
	 * @return
	 * @throws EpanetException
	 */
	public float[] ENgetlinkvalue(int index, EpanetLinkParameter paramcode) throws EpanetException {
		float[] value = new float[2];
		int err = epanet.ENgetlinkvalue(index, paramcode.getCode(), value);
		checkError(err);
		return value;
	}

	/**
	 * 
	 * @return
	 * @throws EpanetException
	 */
	public int ENgetversion() throws EpanetException {
		int[] version = new int[1];
		int err = epanet.ENgetversion(version);
		checkError(err);
		return version[0];
	}

	public void ENsetcontrol(int cindex, int ctype, int lindex, float setting, int nindex, float level)
			throws EpanetException {
		int err = epanet.ENsetcontrol(cindex, ctype, lindex, setting, nindex, level);
		checkError(err);
	}

	/**
	 * 
	 * @param index
	 * @param param
	 * @param value
	 * @throws EpanetException
	 */
	public void ENsetnodevalue(int index, EpanetNodeParameter param, float value) throws EpanetException {
		int err = epanet.ENsetnodevalue(index, param.getCode(), value);
		checkError(err);
	}

	/**
	 * 
	 * @param index
	 * @param param
	 * @param value
	 * @throws EpanetException
	 */
	public void ENsetlinkvalue(int index, EpanetLinkParameter param, float value) throws EpanetException {
		int err = epanet.ENsetlinkvalue(index, param.getCode(), value);
		checkError(err);
	}

	/**
	 * 
	 * @param id
	 * @throws EpanetException
	 */
	public void ENaddpattern(String id) throws EpanetException {
		int err = epanet.ENaddpattern(id);
		checkError(err);
	}

	public void ENsetpattern(int index, float[] factors, int nfactors) throws EpanetException {
		FloatBuffer factorsBuffer = EpanetUtils.floatToFloatBuffer(factors);

		int err = epanet.ENsetpattern(index, factorsBuffer, nfactors);
		checkError(err);
	}

	public void ENsetpatternvalue(int index, int period, float value) throws EpanetException {
		int err = epanet.ENsetpatternvalue(index, period, value);
		checkError(err);
	}

	/**
	 * 
	 * @param param
	 * @param timevalue
	 * @throws EpanetException
	 */
	public void ENsettimeparam(EpanetTimeParameterCodes param, Long timevalue) throws EpanetException {
		int err = epanet.ENsettimeparam(param.getCode(), timevalue);
		checkError(err);
	}

	/**
	 * 
	 * @param option
	 * @param value
	 * @throws EpanetException
	 */
	public void ENsetoption(EpanetOptionCodes option, float value) throws EpanetException {
		int err = epanet.ENsetoption(option.getCode(), value);
		checkError(err);
	}

	/**
	 * 
	 * @param statuslevel
	 * @throws EpanetException
	 */
	public void ENsetstatusreport(int statuslevel) throws EpanetException {
		int err = epanet.ENsetstatusreport(statuslevel);
		checkError(err);
	}

	public void ENsetqualtype(int qualcode, String chemname, String chemunits, String tracenode)
			throws EpanetException {
		ByteBuffer chemnameBuffer = EpanetUtils.stringToByteBuffer(chemname);
		ByteBuffer chemunitsBuffer = EpanetUtils.stringToByteBuffer(chemunits);
		ByteBuffer tracenodeBuffer = EpanetUtils.stringToByteBuffer(tracenode);

		int err = epanet.ENsetqualtype(qualcode, chemnameBuffer, chemunitsBuffer, tracenodeBuffer);
		checkError(err);
	}

	/**
	 * 
	 * @param err
	 * @throws EpanetException
	 */
	private void checkError(int err) throws EpanetException {
		try {
			EpanetErrors.checkError(err);
			warningMessage = EpanetErrors.checkWarning(err);
		} catch (EpanetException e) {
			ENclose();
			throw e;
		}
	}

	/**
	 * Get the warning message if exist.
	 * 
	 * @return Warning message or null if warning message don't exist.
	 */
	private String getWarningMessage() {
		return warningMessage;
	}

	public static void main(String[] args) {
		try {
			EpanetDLL epanet = new EpanetDLL("Epanet20012.dll", "lib/");
			long[] tstep = {1};
			long[] t = {0};
					
			epanet.ENopen("inp/hanoi-Frankenstein.INP", "", "");
			epanet.ENopenH();

			epanet.ENinitH(0);
			do {  
				epanet.ENrunH(t);

				int n_nodes = epanet.ENgetcount(EpanetCountComponentCode.EN_NODECOUNT);

				for (int j = 1; j <= n_nodes; j++) {
					float pressure = epanet.ENgetnodevalue(j, EpanetNodeParameter.EN_PRESSURE);
					System.out.println("Presion nodo "+ j +" es " + pressure);
				}
				
				epanet.ENnextH(tstep);  
			} while (tstep[0] > 0);  

			epanet.ENcloseH();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}