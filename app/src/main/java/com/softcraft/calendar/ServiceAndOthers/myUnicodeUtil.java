package com.softcraft.calendar.ServiceAndOthers;
import java.util.Vector;

public class myUnicodeUtil {
	public static String tsc2unicode(String unicodeni){
		unicodeni = myReplace(unicodeni,"ஜௌ", rep_charret2("¦ƒª"));
		unicodeni = myReplace(unicodeni,"ஜோ", rep_charret2("§ƒ¡"));
		unicodeni = myReplace(unicodeni,"ஜோ", rep_charret2("§ƒ¡"));
		unicodeni = myReplace(unicodeni,"ஜொ", rep_charret2("¦ƒ¡"));
		unicodeni = myReplace(unicodeni,"ஜொ", rep_charret2("¦ƒ¡"));

		unicodeni = myReplace(unicodeni,"ஜா", rep_charret2("ƒ¡"));
		unicodeni = myReplace(unicodeni,"ஜி", rep_charret2("ƒ¢"));
		unicodeni = myReplace(unicodeni,"ஜீ", rep_charret2("ƒ£"));
		unicodeni = myReplace(unicodeni,"ஜு", rep_charret2("ƒ¤"));
		unicodeni = myReplace(unicodeni,"ஜூ", rep_charret2("ƒ¥"));
		unicodeni = myReplace(unicodeni,"ஜெ", rep_charret2("¦ƒ"));
		unicodeni = myReplace(unicodeni,"ஜே", rep_charret2("§ƒ"));
		unicodeni = myReplace(unicodeni,"ஜை", rep_charret2("¨ƒ"));
		//unicodeni = myReplace(unicodeni,"ஜ்", "\\ˆ");
		unicodeni = myReplace(unicodeni,"ஜ்", rep_charret2("ˆ"));
		unicodeni = myReplace(unicodeni,"ஜ்", rep_charret2("\\ˆ"));
		unicodeni = myReplace(unicodeni,"ஜ", rep_charret2("ƒ"));



		unicodeni = myReplace(unicodeni,"கௌ", rep_charret2("¦¸ª"));
		unicodeni = myReplace(unicodeni,"கோ", rep_charret2("§¸¡"));
		unicodeni = myReplace(unicodeni,"கோ", rep_charret2("§¸¡"));
		unicodeni = myReplace(unicodeni,"கொ", rep_charret2("¦¸¡"));
		unicodeni = myReplace(unicodeni,"கொ", rep_charret2("¦¸¡"));
		unicodeni = myReplace(unicodeni,"கா", rep_charret2("¸¡"));
		unicodeni = myReplace(unicodeni,"கி",rep_charret2("¸¢"));
		//String ss="¸"+charret()+"£");
		//strre="¸"+strCtr+"£");
		//unicodeni = myReplace(unicodeni,"கீ", strre);
		unicodeni = myReplace(unicodeni,"கீ", rep_charret2("¸£"));
		unicodeni = myReplace(unicodeni,"கு", "Ì");
		unicodeni = myReplace(unicodeni,"கூ", "Ü");
		unicodeni = myReplace(unicodeni,"கெ", rep_charret2("¦¸"));
		unicodeni = myReplace(unicodeni,"கே", rep_charret2("§¸"));
		unicodeni = myReplace(unicodeni,"கை", rep_charret2("¨¸"));
		unicodeni = myReplace(unicodeni,"க‌ை", rep_charret2("¨¸"));
		unicodeni = myReplace(unicodeni,"க்", rep_charret2("ì"));
		unicodeni = myReplace(unicodeni,"க", rep_charret2("¸"));


		unicodeni = myReplace(unicodeni,"ஙௌ", rep_charret2("¦¹ª"));
		unicodeni = myReplace(unicodeni,"ஙோ", rep_charret2("§¹¡"));
		unicodeni = myReplace(unicodeni,"ஙொ", rep_charret2("¦¹¡"));
		unicodeni = myReplace(unicodeni,"ஙா", rep_charret2("¹¡"));
		unicodeni = myReplace(unicodeni,"ஙி", rep_charret2("¹¢"));
		unicodeni = myReplace(unicodeni,"ஙீ", rep_charret2("¹£"));
		unicodeni = myReplace(unicodeni,"ஙு", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"ஙூ", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"ஙெ", rep_charret2("¦¹"));
		unicodeni = myReplace(unicodeni,"ஙே", rep_charret2("§¹"));
		unicodeni = myReplace(unicodeni,"ஙை", rep_charret2("¨¹"));
		unicodeni = myReplace(unicodeni,"ங்", rep_charret2("í"));
		unicodeni = myReplace(unicodeni,"ங", rep_charret2("¹"));



		unicodeni = myReplace(unicodeni,"சௌ", rep_charret2("¦ºª"));
		unicodeni = myReplace(unicodeni,"சோ", rep_charret2("§º¡"));
		unicodeni = myReplace(unicodeni,"சோ", rep_charret2("§º¡"));
		unicodeni = myReplace(unicodeni,"சொ", rep_charret2("¦º¡"));
		unicodeni = myReplace(unicodeni,"சொ", rep_charret2("¦º¡"));
		unicodeni = myReplace(unicodeni,"சா", rep_charret2("º¡"));
		unicodeni = myReplace(unicodeni,"சி", rep_charret2("º¢"));
		unicodeni = myReplace(unicodeni,"சீ", rep_charret2("º£"));
		unicodeni = myReplace(unicodeni,"சு", rep_charret2("Í"));
		unicodeni = myReplace(unicodeni,"சூ", rep_charret2("Ý"));
		unicodeni = myReplace(unicodeni,"செ", rep_charret2("¦º"));
		unicodeni = myReplace(unicodeni,"சே", rep_charret2("§º"));
		unicodeni = myReplace(unicodeni,"சை", rep_charret2("¨º"));
		unicodeni = myReplace(unicodeni,"ச்", rep_charret2("î"));
		unicodeni = myReplace(unicodeni,"ச", rep_charret2("º"));


		unicodeni = myReplace(unicodeni,"ஞௌ", rep_charret2("¦»ª"));
		unicodeni = myReplace(unicodeni,"ஞோ", rep_charret2("§»¡"));
		unicodeni = myReplace(unicodeni,"ஞொ", rep_charret2("¦»¡"));
		unicodeni = myReplace(unicodeni,"ஞா", rep_charret2("»¡"));
		unicodeni = myReplace(unicodeni,"ஞி", rep_charret2("»¢"));
		unicodeni = myReplace(unicodeni,"ஞீ", rep_charret2("»£"));
		unicodeni = myReplace(unicodeni,"ஞு", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"ஞூ", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"ஞெ", rep_charret2("¦»"));
		unicodeni = myReplace(unicodeni,"ஞே", rep_charret2("§»"));
		unicodeni = myReplace(unicodeni,"ஞை", rep_charret2("¨»"));
		unicodeni = myReplace(unicodeni,"ஞ்", rep_charret2("ï"));
		unicodeni = myReplace(unicodeni,"ஞ", rep_charret2("»"));



		unicodeni = myReplace(unicodeni,"டௌ", rep_charret2("¦¼ª"));
		unicodeni = myReplace(unicodeni,"டோ", rep_charret2("§¼¡"));
		unicodeni = myReplace(unicodeni,"டோ", rep_charret2("§¼¡"));

		unicodeni = myReplace(unicodeni,"டொ", rep_charret2("¦¼¡"));
		unicodeni = myReplace(unicodeni,"டொ", rep_charret2("¦¼¡"));
		unicodeni = myReplace(unicodeni,"டா", rep_charret2("¼¡"));
		unicodeni = myReplace(unicodeni,"டி", rep_charret2("Ê"));
		unicodeni = myReplace(unicodeni,"டீ", rep_charret2("Ë"));
		unicodeni = myReplace(unicodeni,"டு்", rep_charret2("Î"));
		unicodeni = myReplace(unicodeni,"டு", rep_charret2("Î"));
		unicodeni = myReplace(unicodeni,"டூ", rep_charret2("Þ"));
		unicodeni = myReplace(unicodeni,"டெ", rep_charret2("¦¼"));
		unicodeni = myReplace(unicodeni,"டே", rep_charret2("§¼"));
		unicodeni = myReplace(unicodeni,"டை", rep_charret2("¨¼"));
		unicodeni = myReplace(unicodeni,"ட்", rep_charret2("ð"));
		unicodeni = myReplace(unicodeni,"ட", rep_charret2("¼"));


		unicodeni = myReplace(unicodeni,"ணௌ", rep_charret2("¦½ª"));
		unicodeni = myReplace(unicodeni,"ணோ", rep_charret2("§½¡"));
		unicodeni = myReplace(unicodeni,"ணொ", rep_charret2("¦½¡"));
		unicodeni = myReplace(unicodeni,"ணா", rep_charret2("½¡"));
		unicodeni = myReplace(unicodeni,"ணி", rep_charret2("½¢"));
		unicodeni = myReplace(unicodeni,"ணீ", rep_charret2("½£"));
		unicodeni = myReplace(unicodeni,"ணு", rep_charret2("Ï"));
		unicodeni = myReplace(unicodeni,"ணூ", rep_charret2("ß"));
		unicodeni = myReplace(unicodeni,"ணெ", rep_charret2("¦½"));
		unicodeni = myReplace(unicodeni,"ணே", rep_charret2("§½"));
		unicodeni = myReplace(unicodeni,"ணை", rep_charret2("¨½"));
		unicodeni = myReplace(unicodeni,"ண்", rep_charret2("ñ"));
		unicodeni = myReplace(unicodeni,"ண", rep_charret2("½"));

		unicodeni = myReplace(unicodeni,"தௌ", rep_charret2("¦¾ª"));
		unicodeni = myReplace(unicodeni,"தோ", rep_charret2("§¾¡"));
		unicodeni = myReplace(unicodeni,"தோ", rep_charret2("§¾¡"));
		unicodeni = myReplace(unicodeni,"தொ", rep_charret2("¦¾¡")); //kabilan
		unicodeni = myReplace(unicodeni,"தொ", rep_charret2("¦¾¡"));


		unicodeni = myReplace(unicodeni,"தா", rep_charret2("¾¡"));
		unicodeni = myReplace(unicodeni,"தி்", rep_charret2("¾¢"));
		unicodeni = myReplace(unicodeni,"தி", rep_charret2("¾¢"));
		unicodeni = myReplace(unicodeni,"தீ", rep_charret2("¾£"));
		unicodeni = myReplace(unicodeni,"து", rep_charret2("Ð"));
		unicodeni = myReplace(unicodeni,"தூ", rep_charret2("à"));
		unicodeni = myReplace(unicodeni,"தெ", rep_charret2("¦¾"));
		unicodeni = myReplace(unicodeni,"தே", rep_charret2("§¾"));
		unicodeni = myReplace(unicodeni,"தை", rep_charret2("¨¾"));
		unicodeni = myReplace(unicodeni,"த‌ை", rep_charret2("¨¾"));

		unicodeni = myReplace(unicodeni,"த்", rep_charret2("ò"));
		unicodeni = myReplace(unicodeni,"த", rep_charret2("¾"));



		unicodeni = myReplace(unicodeni,"நௌ", rep_charret2("¦¿ª"));
		unicodeni = myReplace(unicodeni,"நோ", rep_charret2("§¿¡"));
		unicodeni = myReplace(unicodeni,"நோ", rep_charret2("§¿¡"));

		unicodeni = myReplace(unicodeni,"நொ", rep_charret2("¦¿¡"));
		unicodeni = myReplace(unicodeni,"நொ", rep_charret2("¦¿¡"));

		unicodeni = myReplace(unicodeni,"நா", rep_charret2("¿¡"));
		unicodeni = myReplace(unicodeni,"நி", rep_charret2("¿¢"));
		unicodeni = myReplace(unicodeni,"நீ", rep_charret2("¿£"));
		unicodeni = myReplace(unicodeni,"நு", rep_charret2("Ñ"));
		unicodeni = myReplace(unicodeni,"நூ", rep_charret2("á"));
		unicodeni = myReplace(unicodeni,"நெ", rep_charret2("¦¿"));
		unicodeni = myReplace(unicodeni,"நே", rep_charret2("§¿"));
		unicodeni = myReplace(unicodeni,"நை", rep_charret2("¨¿"));
		unicodeni = myReplace(unicodeni,"ந்", rep_charret2("ó"));
		unicodeni = myReplace(unicodeni,"ந", rep_charret2("¿"));


		unicodeni = myReplace(unicodeni,"னௌ", rep_charret2("¦Éª"));
		unicodeni = myReplace(unicodeni,"னோ", rep_charret2("§É¡"));
		unicodeni = myReplace(unicodeni,"னோ", rep_charret2("§É¡"));

		unicodeni = myReplace(unicodeni,"னொ", rep_charret2("¦É¡"));
		unicodeni = myReplace(unicodeni,"னொ", rep_charret2("¦É¡"));

		unicodeni = myReplace(unicodeni,"னா", rep_charret2("É¡"));
		unicodeni = myReplace(unicodeni,"னி", rep_charret2("É¢"));
		unicodeni = myReplace(unicodeni,"னீ", rep_charret2("É£"));
		unicodeni = myReplace(unicodeni,"னு", rep_charret2("Û"));
		unicodeni = myReplace(unicodeni,"னூ", rep_charret2("ë"));
		unicodeni = myReplace(unicodeni,"னெ", rep_charret2("¦É"));
		unicodeni = myReplace(unicodeni,"னே", rep_charret2("§É"));
		unicodeni = myReplace(unicodeni,"னை", rep_charret2("¨É"));
		unicodeni = myReplace(unicodeni,"ன்", rep_charret2("ý"));
		unicodeni = myReplace(unicodeni,"ன", rep_charret2("É"));


		unicodeni = myReplace(unicodeni,"பௌ", rep_charret2("¦Àª"));
		unicodeni = myReplace(unicodeni,"ப‌ோ", rep_charret2("§À¡"));
		unicodeni = myReplace(unicodeni,"போ", rep_charret2("§À¡"));
		unicodeni = myReplace(unicodeni,"போ", rep_charret2("§À¡"));
		unicodeni = myReplace(unicodeni,"பொ", rep_charret2("¦À¡"));
		unicodeni = myReplace(unicodeni,"பொ", rep_charret2("¦À¡"));
		unicodeni = myReplace(unicodeni,"ப‌ொ", rep_charret2("¦À¡"));

		unicodeni = myReplace(unicodeni,"பா", rep_charret2("À¡"));
		unicodeni = myReplace(unicodeni,"பி்", rep_charret2("À¢"));
		unicodeni = myReplace(unicodeni,"பி", rep_charret2("À¢"));

		unicodeni = myReplace(unicodeni,"பீ", rep_charret2("À£"));
		unicodeni = myReplace(unicodeni,"பு", rep_charret2("Ò"));
		unicodeni = myReplace(unicodeni,"பூ", rep_charret2("â"));
		unicodeni = myReplace(unicodeni,"பெ", rep_charret2("¦À"));
		unicodeni = myReplace(unicodeni,"பே", rep_charret2("§À"));
		unicodeni = myReplace(unicodeni,"பை", rep_charret2("¨À"));
		unicodeni = myReplace(unicodeni,"ப்",rep_charret2( "ô"));
		unicodeni = myReplace(unicodeni,"ப", rep_charret2("À"));


		unicodeni = myReplace(unicodeni,"மௌ", rep_charret2("¦Áª"));
		unicodeni = myReplace(unicodeni,"மோ", rep_charret2("§Á¡"));
		unicodeni = myReplace(unicodeni,"மோ", rep_charret2("§Á¡"));
		unicodeni = myReplace(unicodeni,"மொ", rep_charret2("¦Á¡"));
		unicodeni = myReplace(unicodeni,"மொ", rep_charret2("¦Á¡"));
		unicodeni = myReplace(unicodeni,"மா", rep_charret2("Á¡"));
		unicodeni = myReplace(unicodeni,"மி", rep_charret2("Á¢"));
		unicodeni = myReplace(unicodeni,"மி்", rep_charret2("Á¢"));
		//strre="Á"+strCtr+"£");
		unicodeni = myReplace(unicodeni,"மீ", rep_charret2("Á£"));
		unicodeni = myReplace(unicodeni,"மு", rep_charret2("Ó"));
		unicodeni = myReplace(unicodeni,"மூ", rep_charret2("ã"));
		unicodeni = myReplace(unicodeni,"மெ", rep_charret2("¦Á"));
		unicodeni = myReplace(unicodeni,"மே", rep_charret2("§Á"));
		unicodeni = myReplace(unicodeni,"மை", rep_charret2("¨Á"));
		unicodeni = myReplace(unicodeni,"ம்", rep_charret2("õ"));
		unicodeni = myReplace(unicodeni,"ம", rep_charret2("Á"));


		unicodeni = myReplace(unicodeni,"யௌ", rep_charret2("¦Âª"));
		unicodeni = myReplace(unicodeni,"யோ", rep_charret2("§Â¡"));
		unicodeni = myReplace(unicodeni,"யோ", rep_charret2("§Â¡"));
		unicodeni = myReplace(unicodeni,"‌யொ", rep_charret2("¦Â¡"));
		unicodeni = myReplace(unicodeni,"யொ", rep_charret2("¦Â¡"));
		unicodeni = myReplace(unicodeni,"யா", rep_charret2("Â¡"));
		unicodeni = myReplace(unicodeni,"யி்", rep_charret2("Â¢"));//
		unicodeni = myReplace(unicodeni,"யி", rep_charret2("Â¢"));
		unicodeni = myReplace(unicodeni,"யீ", rep_charret2("Â£"));
		unicodeni = myReplace(unicodeni,"யு", rep_charret2("Ô"));
		unicodeni = myReplace(unicodeni,"யூ", rep_charret2("ä"));
		unicodeni = myReplace(unicodeni,"யெ", rep_charret2("¦Â"));
		unicodeni = myReplace(unicodeni,"யே", rep_charret2("§Â"));
		unicodeni = myReplace(unicodeni,"யை", rep_charret2("¨Â"));
		unicodeni = myReplace(unicodeni,"ய்", rep_charret2("ö"));
		unicodeni = myReplace(unicodeni,"ய", rep_charret2("Â"));

		unicodeni = myReplace(unicodeni,"ரௌ", rep_charret2("¦Ãª"));
		unicodeni = myReplace(unicodeni,"ரோ", rep_charret2("§Ã¡"));
		unicodeni = myReplace(unicodeni,"ரோ", rep_charret2("§Ã¡"));
		unicodeni = myReplace(unicodeni,"ரொ", rep_charret2("¦Ã¡"));
		unicodeni = myReplace(unicodeni,"ரொ", rep_charret2("¦Ã¡"));
		unicodeni = myReplace(unicodeni,"ரா", rep_charret2("Ã¡"));
		unicodeni = myReplace(unicodeni,"ரி்", rep_charret2("Ã¢"));
		unicodeni = myReplace(unicodeni,"ரி", rep_charret2("Ã¢"));
		unicodeni = myReplace(unicodeni,"ரீ", rep_charret2("Ã£"));
		unicodeni = myReplace(unicodeni,"ரு", rep_charret2("Õ"));
		unicodeni = myReplace(unicodeni,"ரூ", rep_charret2("å"));
		unicodeni = myReplace(unicodeni,"ரெ", rep_charret2("¦Ã"));
		unicodeni = myReplace(unicodeni,"ரே", rep_charret2("§Ã"));
		unicodeni = myReplace(unicodeni,"ரை", rep_charret2("¨Ã"));
		unicodeni = myReplace(unicodeni,"ர‌ை", rep_charret2("¨Ã"));
		unicodeni = myReplace(unicodeni,"ர்", rep_charret2("÷"));
		unicodeni = myReplace(unicodeni,"ர", rep_charret2("Ã"));


		unicodeni = myReplace(unicodeni,"லௌ", rep_charret2("¦Äª"));
		unicodeni = myReplace(unicodeni,"லோ", rep_charret2("§Ä¡"));
		unicodeni = myReplace(unicodeni,"லோ", rep_charret2("§Ä¡"));
		unicodeni = myReplace(unicodeni,"லொ", rep_charret2("¦Ä¡"));
		unicodeni = myReplace(unicodeni,"லொ", rep_charret2("¦Ä¡"));
		unicodeni = myReplace(unicodeni,"லா", rep_charret2("Ä¡"));
		unicodeni = myReplace(unicodeni,"லி்", rep_charret2("Ä¢"));//
		unicodeni = myReplace(unicodeni,"லி", rep_charret2("Ä¢"));
		unicodeni = myReplace(unicodeni,"லீ", rep_charret2("Ä£"));
		unicodeni = myReplace(unicodeni,"லு", rep_charret2("Ö"));
		unicodeni = myReplace(unicodeni,"லூ", rep_charret2("æ"));
		unicodeni = myReplace(unicodeni,"லெ", rep_charret2("¦Ä"));
		unicodeni = myReplace(unicodeni,"லே", rep_charret2("§Ä"));
		unicodeni = myReplace(unicodeni,"லை", rep_charret2("¨Ä"));
		unicodeni = myReplace(unicodeni,"ல்", rep_charret2("ø"));
		unicodeni = myReplace(unicodeni,"ல", rep_charret2("Ä"));


		unicodeni = myReplace(unicodeni,"ளௌ", rep_charret2("¦Çª"));
		unicodeni = myReplace(unicodeni,"ளோ", rep_charret2("§Ç¡"));
		unicodeni = myReplace(unicodeni,"ளோ", rep_charret2("§Ç¡"));
		unicodeni = myReplace(unicodeni,"ளொ", rep_charret2("¦Ç¡"));
		unicodeni = myReplace(unicodeni,"ளா", rep_charret2("Ç¡"));
		unicodeni = myReplace(unicodeni,"ளி்", rep_charret2("Ç¢"));
		unicodeni = myReplace(unicodeni,"ளி", rep_charret2("Ç¢"));
		unicodeni = myReplace(unicodeni,"ளீ", rep_charret2("Ç£"));
		unicodeni = myReplace(unicodeni,"ளு", rep_charret2("Ù"));
		unicodeni = myReplace(unicodeni,"ளூ", rep_charret2("é"));
		unicodeni = myReplace(unicodeni,"ளெ", rep_charret2("¦Ç"));
		unicodeni = myReplace(unicodeni,"ளே", rep_charret2("§Ç"));
		unicodeni = myReplace(unicodeni,"ளை", rep_charret2("¨Ç"));
		unicodeni = myReplace(unicodeni,"ள‌ை", rep_charret2("¨Ç"));

		unicodeni = myReplace(unicodeni,"ள்", rep_charret2("û"));
		unicodeni = myReplace(unicodeni,"ள", rep_charret2("Ç"));

		unicodeni = myReplace(unicodeni,"வௌ", rep_charret2("¦Åª"));
		unicodeni = myReplace(unicodeni,"வோ", rep_charret2("§Å¡"));
		unicodeni = myReplace(unicodeni,"வோ", rep_charret2("§Å¡"));
		unicodeni = myReplace(unicodeni,"வொ", rep_charret2("¦Å¡"));
		unicodeni = myReplace(unicodeni,"வொ", rep_charret2("¦Å¡"));
		unicodeni = myReplace(unicodeni,"வா", rep_charret2("Å¡"));
		unicodeni = myReplace(unicodeni,"வி", rep_charret2("Å¢"));
		unicodeni = myReplace(unicodeni,"வீ", rep_charret2("Å£"));
		unicodeni = myReplace(unicodeni,"வு", rep_charret2("×"));
		unicodeni = myReplace(unicodeni,"வூ", rep_charret2("ç"));
		unicodeni = myReplace(unicodeni,"வெ", rep_charret2("¦Å"));
		unicodeni = myReplace(unicodeni,"வே", rep_charret2("§Å"));
		unicodeni = myReplace(unicodeni,"வை", rep_charret2("¨Å"));
		unicodeni = myReplace(unicodeni,"வ்", rep_charret2("ù"));
		unicodeni = myReplace(unicodeni,"வ", rep_charret2("Å"));


		unicodeni = myReplace(unicodeni,"ழௌ", rep_charret2("¦Æª"));
		unicodeni = myReplace(unicodeni,"ழோ", rep_charret2("§Æ¡"));
		unicodeni = myReplace(unicodeni,"ழொ", rep_charret2("¦Æ¡"));
		unicodeni = myReplace(unicodeni,"ழா", rep_charret2("Æ¡"));
		unicodeni = myReplace(unicodeni,"ழி", rep_charret2("Æ¢"));
		unicodeni = myReplace(unicodeni,"ழீ", rep_charret2("Æ£"));
		unicodeni = myReplace(unicodeni,"ழு", rep_charret2("Ø"));
		unicodeni = myReplace(unicodeni,"ழூ", rep_charret2("è"));
		unicodeni = myReplace(unicodeni,"ழெ", rep_charret2("¦Æ"));
		unicodeni = myReplace(unicodeni,"ழே", rep_charret2("§Æ"));
		unicodeni = myReplace(unicodeni,"ழை", rep_charret2("¨Æ"));
		unicodeni = myReplace(unicodeni,"ழ‌ை", rep_charret2("¨Æ"));
		unicodeni = myReplace(unicodeni,"ழ்", rep_charret2("ú"));
		unicodeni = myReplace(unicodeni,"ழ", rep_charret2("Æ"));

		unicodeni = myReplace(unicodeni,"றௌ", rep_charret2("¦Èª"));
		unicodeni = myReplace(unicodeni,"றோ", rep_charret2("§È¡"));
		unicodeni = myReplace(unicodeni,"றொ", rep_charret2("¦È¡"));
		unicodeni = myReplace(unicodeni,"றொ", rep_charret2("¦È¡"));
		unicodeni = myReplace(unicodeni,"றா", rep_charret2("È¡"));
		unicodeni = myReplace(unicodeni,"றி", rep_charret2("È¢"));
		unicodeni = myReplace(unicodeni,"றீ", rep_charret2("È£"));
		unicodeni = myReplace(unicodeni,"று", rep_charret2("Ú"));
		unicodeni = myReplace(unicodeni,"றூ", rep_charret2("ê"));
		unicodeni = myReplace(unicodeni,"றெ", rep_charret2("¦È"));
		unicodeni = myReplace(unicodeni,"றே", rep_charret2("§È"));
		unicodeni = myReplace(unicodeni,"றை", rep_charret2("¨È"));
		unicodeni = myReplace(unicodeni,"ற்", rep_charret2("ü"));
		unicodeni = myReplace(unicodeni,"ற", rep_charret2("È"));

		unicodeni = myReplace(unicodeni,"ஹௌ", rep_charret2("¦†ª"));
		unicodeni = myReplace(unicodeni,"ஹோ", rep_charret2("§†¡"));
		unicodeni = myReplace(unicodeni,"ஹோ", rep_charret2("§†¡"));
		unicodeni = myReplace(unicodeni,"ஹொ", rep_charret2("¦†¡"));
		unicodeni = myReplace(unicodeni,"ஹா", rep_charret2("†¡"));
		unicodeni = myReplace(unicodeni,"ஹி", rep_charret2("†¢"));
		unicodeni = myReplace(unicodeni,"ஹீ", rep_charret2("†£"));
		unicodeni = myReplace(unicodeni,"ஹு", rep_charret2("†¤"));
		unicodeni = myReplace(unicodeni,"ஹூ", rep_charret2("†¥"));
		unicodeni = myReplace(unicodeni,"ஹெ", rep_charret2("¦†"));
		unicodeni = myReplace(unicodeni,"ஹே", rep_charret2("§†"));
		//unicodeni = myReplace(unicodeni,"ஹை", rep_charret2("¨");
		unicodeni = myReplace(unicodeni,"ஹை", rep_charret2("¨†"));
		unicodeni = myReplace(unicodeni,"ஹ்", rep_charret2("‹ "));
		//RSK unicodeni = myReplace(unicodeni,"ஹ", "†");
		unicodeni = myReplace(unicodeni,"ஹ", rep_charret2("†"));



		unicodeni = myReplace(unicodeni,"ஷௌ", rep_charret2("¦„ª"));
		unicodeni = myReplace(unicodeni,"ஷோ", rep_charret2("§„¡"));
		unicodeni = myReplace(unicodeni,"ஷோ", rep_charret2("§„¡"));

		unicodeni = myReplace(unicodeni,"ஷொ", rep_charret2("¦„¡"));
		unicodeni = myReplace(unicodeni,"ஷா", rep_charret2("„¡"));
		unicodeni = myReplace(unicodeni,"ஷி", rep_charret2("„¢"));
		unicodeni = myReplace(unicodeni,"ஷீ", rep_charret2("„£"));
		unicodeni = myReplace(unicodeni,"ஷு", rep_charret2("„¤"));
		unicodeni = myReplace(unicodeni,"ஷூ", rep_charret2("„¥"));
		unicodeni = myReplace(unicodeni,"ஷெ", rep_charret2("¦„"));
		unicodeni = myReplace(unicodeni,"ஷே", rep_charret2("§„"));
		unicodeni = myReplace(unicodeni,"ஷை", rep_charret2("¨„"));
		unicodeni = myReplace(unicodeni,"ஷ்", rep_charret2("‰"));
		unicodeni = myReplace(unicodeni,"ஷ", rep_charret2("„"));



		unicodeni = myReplace(unicodeni,"ஸௌ", rep_charret2("¦…ª"));
		unicodeni = myReplace(unicodeni,"ஸோ", rep_charret2("§…¡"));
		unicodeni = myReplace(unicodeni,"ஸொ", rep_charret2("¦…¡"));
		unicodeni = myReplace(unicodeni,"ஸா", rep_charret2("…¡"));
		unicodeni = myReplace(unicodeni,"ஸி", rep_charret2("…¢"));
		unicodeni = myReplace(unicodeni,"ஸீ", rep_charret2("…£"));
		unicodeni = myReplace(unicodeni,"ஸு", rep_charret2("…¤"));
		unicodeni = myReplace(unicodeni,"ஸூ", rep_charret2("…¥"));
		unicodeni = myReplace(unicodeni,"ஸெ", rep_charret2("¦…"));
		unicodeni = myReplace(unicodeni,"ஸே", rep_charret2("§…"));
		unicodeni = myReplace(unicodeni,"ஸை", rep_charret2("¨…"));
		unicodeni = myReplace(unicodeni,"ஸ்", rep_charret2("Š"));
		unicodeni = myReplace(unicodeni,"ஸ", rep_charret2("…"));


		unicodeni = myReplace(unicodeni,"அ", rep_charret2("«"));
		unicodeni = myReplace(unicodeni,"ஆ", rep_charret2("¬"));
		unicodeni = myReplace(unicodeni,"இ", rep_charret2("þ"));
		unicodeni = myReplace(unicodeni,"ஈ", rep_charret2("®"));
		unicodeni = myReplace(unicodeni,"உ", rep_charret2("¯"));
		unicodeni = myReplace(unicodeni,"ஊ", rep_charret2("°"));
		unicodeni = myReplace(unicodeni,"எ", rep_charret2("±"));
		unicodeni = myReplace(unicodeni,"ஏ", rep_charret2("²"));
		unicodeni = myReplace(unicodeni,"ஐ", rep_charret2("³"));
		unicodeni = myReplace(unicodeni,"ஒ", rep_charret2("´"));
		unicodeni = myReplace(unicodeni,"ஓ", rep_charret2("µ"));
		unicodeni = myReplace(unicodeni,"ஒள", rep_charret2("¶"));
		unicodeni = myReplace(unicodeni,"ஔ", rep_charret2("¶"));

		unicodeni = myReplace(unicodeni,"ஃ", rep_charret2("·"));
		unicodeni = myReplace(unicodeni,"ஸ்ரீ",rep_charret2( ""));
		unicodeni = myReplace(unicodeni,"‘", rep_charret2("‘"));

		unicodeni = myReplace(unicodeni,"௧", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௨", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௩", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௪", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௫", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௰", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௱", rep_charret2(""));
		unicodeni = myReplace(unicodeni,"௲", rep_charret2("Ÿ"));
		unicodeni = myReplace(unicodeni,"௭", rep_charret2("–"));
		unicodeni = myReplace(unicodeni,"௮", rep_charret2("—"));
		unicodeni = myReplace(unicodeni,"௯", rep_charret2("˜"));
		unicodeni = myReplace(unicodeni,"௲", rep_charret2("Ÿ"));
		unicodeni = myReplace(unicodeni,"௭", rep_charret2("–"));

		return unicodeni;
	}

	public static String myReplace(String FullString, String ReplacementString,String SearchString)
	{
		StringBuffer sb = new StringBuffer();

		try{

			int searchStringPos = FullString.indexOf(SearchString);
			int start=0;
			int end=SearchString.length();

			while (searchStringPos!=-1)
			{
				sb.append(FullString.substring(start,searchStringPos)).append(ReplacementString);
				start=searchStringPos+end;
				searchStringPos=FullString.indexOf(SearchString,start);
			}
			sb.append(FullString.substring(start, FullString.length()));

		}catch (Exception e) {
			// TODO: handle exception
		}

		return sb.toString();
	}

	public static String rep_charret2(String str)
	{
		int len=str.length();
		Vector a1 = new Vector();
		int mycode=8288;
		if(len==1)
		{
			//	mycode=233;
			//	a1.addElement(new Character((char) mycode));
			a1.addElement(new Character(str.charAt(0)));
			a1.addElement(new Character((char) mycode));
		}
		else if(len==2)
		{
			a1.addElement(new Character(str.charAt(0)));
			a1.addElement(new Character((char) mycode));
			a1.addElement(new Character(str.charAt(1)));
			a1.addElement(new Character((char) mycode));
		}
		else if(len==3)
		{
			a1.addElement(new Character(str.charAt(0)));
			a1.addElement(new Character((char) mycode));
			a1.addElement(new Character(str.charAt(1)));
			a1.addElement(new Character((char) mycode));
			a1.addElement(new Character(str.charAt(2)));
			a1.addElement(new Character((char) mycode));
		}
		String ret = new String();
		for(int j=0;j<a1.size();j++)
		{
			ret = ret +((Character) (a1.elementAt(j))).charValue();
		}
		return ret;
	}

}