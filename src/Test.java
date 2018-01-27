import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import util.FileStreamUtil;


public class Test {
	
	private Set<String> reads = new HashSet<String>();
	
	public void read(String filename,int offset){
		String readid;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			str = br.readLine();
			while(str != null){
				readid = str.substring(0, offset);
				reads.add(readid);
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Map<String,String> map = new HashMap<String,String>();
	public void readAndoutput(String filename, String outFile){
		map.clear();
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
			String str = br.readLine();
			bw.write(str);
			bw.newLine();
			String readid;
			str = br.readLine();
			while(str != null){
				readid = str.substring(0, 16);
				if(map.get(readid) == null){
					map.put(readid, str);
				}
				str = br.readLine();
			}
			br.close();
			
			Iterator<String> it = map.values().iterator();
			while(it.hasNext()){
				bw.write(it.next());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Test() {
//		for(int i=1;i<=22;i++){
//			readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/Chr"+i+"_crick_CT", 
//					"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/Chr"+i+"_crick_CT");
//			readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/Chr"+i+"_crick_GA", 
//					"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/Chr"+i+"_crick_GA");
//			readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/Chr"+i+"_watson_CT", 
//					"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/Chr"+i+"_watson_CT");
//			readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/Chr"+i+"_watson_GA", 
//					"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/Chr"+i+"_watson_GA");
//		}
//		readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/ChrX_crick_CT", 
//				"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/ChrX_crick_CT");
//		readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/ChrX_crick_GA", 
//				"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/ChrX_crick_GA");
//		readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/ChrX_watson_CT", 
//				"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/ChrX_watson_CT");
//		readAndoutput("E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/ChrX_watson_GA", 
//				"E:/研究生工作/personal_data/1999.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/divide/bwa/temp/ChrX_watson_GA");
//		
//		if(1==1)return;
		
		/*****************计算原始reads  bwa  map reads数目（已经去除repeat了）********************/ 
//			read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map all/crick_CT.txt",14);
//			System.out.println(reads.size());
//			read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map all/crick_GA.txt",14);
//			System.out.println(reads.size());
//			read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map all/watson_CT.txt",14);
//			System.out.println(reads.size());
//			read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map all/watson_GA.txt",14);
//			System.out.println(reads.size());
		
		/***************reads分割后 map的数目*****************/
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map split/crick_CT.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map split/crick_GA.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map split/watson_CT.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Map split/watson_GA.txt",16);
//		System.out.println(reads.size());
		
		/***************未匹配的reads分割后 map的数目*****************/
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Unmappble split/crick_CT.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Unmappble split/crick_GA.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Unmappble split/watson_CT.txt",16);
//		System.out.println(reads.size());
//		read("E:/研究生工作/personal_data/1999.GAC.454Reads/1999.GAC.454Reads匹配结果/Unmappble split/watson_GA.txt",16);
//		System.out.println(reads.size());
		
			
		/************************计算分割reads数目**************************/
		readOrigin("E:/研究生工作/personal_data/o6.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/maxprecision/divide/crick_CT.txt");
		readOrigin("E:/研究生工作/personal_data/o6.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/maxprecision/divide/crick_GA.txt");
		readOrigin("E:/研究生工作/personal_data/o6.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/maxprecision/divide/watson_CT.txt");
		readOrigin("E:/研究生工作/personal_data/o6.GAC.454Reads/bwa匹配结果/FormatConvert/Fusion/maxprecision/divide/watson_GA.txt");
		System.out.println(reads.size());
	}
	
	
	public void readOrigin(String filename){
		String readid;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
//			str = br.readLine();
			while(str != null){
				if(str.startsWith(">")){
					readid = str.substring(1, 15);  //15 表示分割条数   17-表示分割后得到的条数
					reads.add(readid);
				}
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//			new Test();
		//630
		String str1 = "30	31	32	33	34	35	36	37	38	39	40	41	42	43	44	45	46	47	48	49	50	51	52	53	54	55	56	57	58	59	60	61	62	63	64	65	66	67	68	69	70	71	72	73	74	75	76	77	78	79	80	81	82	83	84	85	86	87	88	89	90	91	92	93	94	95	96	97	98	99	100	101	102	103	104	105	106	107	108	109	110	111	112	113	114	115	116	117	118	119	120	121	122	123	124	125	126	127	128	129	130	131	132	133	134	135	136	137	138	139	140	141	142	143	144	145	146	147	148	149	150	151	152	153	154	155	156	157	158	159	160	161	162	163	164	165	166	167	168	169	170	171	172	173	174	175	176	177	178	179	180	181	182	183	184	185	186	187	188	189	190	191	192	193	194	195	196	197	198	199	200	201	202	203	204	205	206	207	208	209	210	211	212	213	214	215	216	217	218	219	220	221	222	223	224	225	226	227	228	229	230	231	232	233	234	235	236	237	238	239	240	241	242	243	244	245	246	247	248	249	250	251	252	253	254	255	256	257	258	259	260	261	262	263	264	265	266	267	268	269	270	271	272	273	274	275	276	277	278	279	280	281	282	283	284	285	286	287	288	289	290	291	292	293	294	295	296	297	298	299	300	301	302	303	304	305	306	307	308	309	310	311	312	313	314	315	316	317	318	319	320	321	322	323	324	325	326	327	328	329	330	331	332	333	334	335	336	337	338	339	340	341	342	343	344	345	346	347	348	349	350	351	352	353	354	355	356	357	358	359	360	361	362	363	364	365	366	367	368	369	370	371	372	373	374	375	376	377	378	379	380	381	382	383	384	385	386	387	388	389	390	391	392	393	394	395	396	397	398	399	400	401	402	403	404	405	406	407	408	409	410	411	412	413	414	415	416	417	418	419	420	421	422	423	424	425	426	427	428	429	430	431	432	433	434	435	436	437	438	439	440	441	442	443	444	445	446	447	448	449	450	451	452	453	454	455	456	457	458	459	460	461	462	463	464	465	466	467	468	469	470	471	472	473	474	475	476	477	478	479	480	481	482	483	484	485	486	487	488	489	490	491	492	493	494	495	496	497	498	499	500	501	502	503	504	505	506	507	508	509	510	511	512	513	514	515	516	517	518	519	520	521	522	523	524	525	526	527	528	529	530	531	532	533	534	535	536	537	538	539	540	541	542	543	544	545	546	547	548	549	550	551	552	553	554	555	556	557	558	559	560	561	562	563	564	565	566	567	568	569	570	571	572	573	574	575	576	577	578	579	580	581	582	583	584	585	586	587	588	589	590	591	592	593	594	595	596	597	598	599	600	601	602	603	604	605	606	607	608	609	610	611	612	613	614	615	616	617	618	619	620	621	622	623	624	625	626	627	628	631	632	636	637	641	643	646	649	652	660	663	664	672	674	690	699	701	717	729	743	750	753	792	816	837	1026	1436	1953	2032	2043	2044";
		//535
		String str2 = "301	356	388	447	532	725	997	1713	2888	5425	9587	16385	25875	37604	53211	69216	86832	103910	119680	133922	146714	157163	166344	173146	176666	180274	182592	184987	185711	186293	185138	185043	184661	182124	181045	180121	179069	176761	174350	172942	171871	169697	167853	165613	163594	161023	158780	157919	154281	153108	150722	148782	145914	143627	141882	139088	137067	135637	134825	132546	131945	130159	128551	128266	126896	127427	125725	125747	125807	126222	126305	126663	126277	126282	127600	127075	126489	127588	127948	127452	128066	127257	127531	126836	126422	126404	125314	124562	124221	123294	122464	121905	121555	120287	119311	118758	118592	116826	115512	114891	114276	112860	112611	111335	110587	109635	108936	108199	108008	106620	106439	105366	105597	104836	104854	105077	104849	105312	105824	105738	106859	106661	107297	108063	109699	110497	111188	112670	113051	114198	114642	115469	116374	117090	118180	118908	119081	118915	121178	121117	120766	121888	121650	121865	122458	122683	123021	122619	122829	122895	123713	123113	122590	122551	122109	121457	122317	122044	122735	122691	122635	123056	123305	123870	123564	124825	124772	125204	126206	127108	128186	129284	130026	131424	133511	134399	136434	138249	140908	141577	144527	146057	148282	150853	153793	157123	160351	164240	167649	171951	176725	182451	188201	194583	200645	207588	214161	220674	229095	238878	247757	257377	268638	279738	292204	306301	320592	336791	354103	368849	387335	405564	424152	443799	462239	482368	500064	518260	533123	549239	563015	576688	586188	598115	606060	611256	613745	616175	617107	614623	606647	602646	595054	585297	573175	562213	548610	534821	520099	504179	489356	474311	457103	439789	424155	407639	392633	376257	361226	346249	333215	319030	305297	293083	281284	269677	260860	248781	240256	230850	221967	214845	206963	200337	194392	188125	182458	178332	174106	170077	167793	163709	161626	159998	158092	155607	154421	153364	151322	150790	150637	150183	149639	149067	149627	149563	150141	150227	150524	150536	152494	152124	153488	154566	156029	155763	156710	157547	158547	159090	160772	161337	162961	163948	165241	166271	167046	168582	169443	171044	171865	173524	174587	175563	176102	177302	178894	179829	181607	182659	182015	184578	185984	186617	186643	188444	190387	191728	192479	191913	194099	195396	196155	196678	196556	197571	198235	198128	198117	199805	199526	200782	201420	202916	203460	204339	204638	205401	205492	205345	206561	206219	207554	208447	209082	209469	210075	210046	210360	210213	210261	210628	211335	210945	209594	211130	210348	210692	209976	210987	210769	210714	209658	208487	209325	208975	209453	209358	208146	208113	207617	208497	207858	206694	206137	205179	204690	205099	205001	213573	212863	209778	205542	201685	198732	194447	192694	189829	187896	183075	181268	179407	178607	178013	176723	174812	173799	173486	172025	170304	169692	169218	168854	166833	166214	164939	164213	163416	161725	160701	160046	158000	157182	156815	156064	154700	153609	151745	151049	149980	149128	147660	147195	145953	144948	143777	142638	141536	140901	140224	138628	137906	137292	135533	134639	134123	133659	131977	131137	129952	128867	127246	126397	125173	123889	122658	121400	120591	119501	117266	116000	114705	112956	111566	110445	108399	106154	103885	102319	99745	98378	95268	93970	91764	88854	86617	84680	82044	79774	76905	74765	72803	69930	67618	64648	62991	60392	58009	55807	53190	51433	49053	46016	44167	42230	40167	37824	35844	34258	31990	30627	29253	27588	25573	23820	22680	21134	20221	18800	17785	16475	15363	14457	13340	12375	11701	10464	10126	9249	8542	7917	7305	6785	6216	5835	5260	4811	4499	4046	3691	3426	3013	2935	2579	225";
		//96
		String str3 = "8	2130	1961	1765	1603	1482	1390	1256	1171	1011	999	811	744	660	592	552	501	455	420	365	354	276	270	225	210	208	179	156	135	125	116	100	81	58	71	67	60	43	42	39	39	19	23	19	19	18	16	9	10	10	4	1	10	2	6	8	7	8	5	5	6	1	4	2	1	2	1	1	2	1	1	1	1	3	1	1	1	1	1	1	1	1	1	1	2	1	1	1	1	1	1	1	1	1	1	1";
		String[] s = str1.split("\t");
		System.out.println(s.length);
	}

}
