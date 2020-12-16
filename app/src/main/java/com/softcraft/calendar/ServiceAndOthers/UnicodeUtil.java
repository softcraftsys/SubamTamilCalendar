package com.softcraft.calendar.ServiceAndOthers;
import android.os.Build;

import java.util.Vector;

public class UnicodeUtil {
	public static String unicode2tsc(String unicodeni){
		unicodeni = replace(unicodeni,"ஜௌ", rep_charret2("¦ƒª"));
		unicodeni = replace(unicodeni,"ஜோ", rep_charret2("§ƒ¡"));
		unicodeni = replace(unicodeni,"ஜோ", rep_charret2("§ƒ¡"));
		unicodeni = replace(unicodeni,"ஜொ", rep_charret2("¦ƒ¡"));
		unicodeni = replace(unicodeni,"ஜொ", rep_charret2("¦ƒ¡"));

		unicodeni = replace(unicodeni,"ஜா", rep_charret2("ƒ¡"));
		unicodeni = replace(unicodeni,"ஜி", rep_charret2("ƒ¢"));
		unicodeni = replace(unicodeni,"ஜீ", rep_charret2("ƒ£"));
		unicodeni = replace(unicodeni,"ஜு", rep_charret2("ƒ¤"));
		unicodeni = replace(unicodeni,"ஜூ", rep_charret2("ƒ¥"));
		unicodeni = replace(unicodeni,"ஜெ", rep_charret2("¦ƒ"));
		unicodeni = replace(unicodeni,"ஜே", rep_charret2("§ƒ"));
		unicodeni = replace(unicodeni,"ஜை", rep_charret2("¨ƒ"));
		//unicodeni = replace(unicodeni,"ஜ்", "\\ˆ");
		unicodeni = replace(unicodeni,"ஜ்", rep_charret2("ˆ"));
		unicodeni = replace(unicodeni,"ஜ்", rep_charret2("\\ˆ"));
		unicodeni = replace(unicodeni,"ஜ", rep_charret2("ƒ"));

		//சந்தி்த்தார்


		unicodeni = replace(unicodeni,"கௌ", rep_charret2("¦¸ª"));
		unicodeni = replace(unicodeni,"கோ", rep_charret2("§¸¡"));
		unicodeni = replace(unicodeni,"கோ", rep_charret2("§¸¡"));
		unicodeni = replace(unicodeni,"கொ", rep_charret2("¦¸¡"));
		unicodeni = replace(unicodeni,"கொ", rep_charret2("¦¸¡"));
		unicodeni = replace(unicodeni,"கா", rep_charret2("¸¡"));
		unicodeni = replace(unicodeni,"கி",rep_charret2("¸¢"));
		//String ss="¸"+charret()+"£");
		//strre="¸"+strCtr+"£");
		//unicodeni = replace(unicodeni,"கீ", strre);
		unicodeni = replace(unicodeni,"கீ", rep_charret2("¸£"));
		unicodeni = replace(unicodeni,"கு", "Ì");
		unicodeni = replace(unicodeni,"கூ", "Ü");
		unicodeni = replace(unicodeni,"கெ", rep_charret2("¦¸"));
		unicodeni = replace(unicodeni,"கே", rep_charret2("§¸"));
		unicodeni = replace(unicodeni,"கை", rep_charret2("¨¸"));
		unicodeni = replace(unicodeni,"க‌ை", rep_charret2("¨¸"));
		unicodeni = replace(unicodeni,"க்", rep_charret2("ì"));
		unicodeni = replace(unicodeni,"க", rep_charret2("¸"));


		unicodeni = replace(unicodeni,"ஙௌ", rep_charret2("¦¹ª"));
		unicodeni = replace(unicodeni,"ஙோ", rep_charret2("§¹¡"));
		unicodeni = replace(unicodeni,"ஙொ", rep_charret2("¦¹¡"));
		unicodeni = replace(unicodeni,"ஙா", rep_charret2("¹¡"));
		unicodeni = replace(unicodeni,"ஙி", rep_charret2("¹¢"));
		unicodeni = replace(unicodeni,"ஙீ", rep_charret2("¹£"));
		unicodeni = replace(unicodeni,"ஙு", rep_charret2(""));
		unicodeni = replace(unicodeni,"ஙூ", rep_charret2(""));
		unicodeni = replace(unicodeni,"ஙெ", rep_charret2("¦¹"));
		unicodeni = replace(unicodeni,"ஙே", rep_charret2("§¹"));
		unicodeni = replace(unicodeni,"ஙை", rep_charret2("¨¹"));
		unicodeni = replace(unicodeni,"ங்", rep_charret2("í"));
		unicodeni = replace(unicodeni,"ங", rep_charret2("¹"));



		unicodeni = replace(unicodeni,"சௌ", rep_charret2("¦ºª"));
		unicodeni = replace(unicodeni,"சோ", rep_charret2("§º¡"));
		unicodeni = replace(unicodeni,"சோ", rep_charret2("§º¡"));
		unicodeni = replace(unicodeni,"சொ", rep_charret2("¦º¡"));
		unicodeni = replace(unicodeni,"சொ", rep_charret2("¦º¡"));
		unicodeni = replace(unicodeni,"சா", rep_charret2("º¡"));
		unicodeni = replace(unicodeni,"சி", rep_charret2("º¢"));
		unicodeni = replace(unicodeni,"சீ", rep_charret2("º£"));
		unicodeni = replace(unicodeni,"சு", rep_charret2("Í"));
		unicodeni = replace(unicodeni,"சூ", rep_charret2("Ý"));
		unicodeni = replace(unicodeni,"செ", rep_charret2("¦º"));
		unicodeni = replace(unicodeni,"சே", rep_charret2("§º"));
		unicodeni = replace(unicodeni,"சை", rep_charret2("¨º"));
		unicodeni = replace(unicodeni,"ச்", rep_charret2("î"));
		unicodeni = replace(unicodeni,"ச", rep_charret2("º"));


		unicodeni = replace(unicodeni,"ஞௌ", rep_charret2("¦»ª"));
		unicodeni = replace(unicodeni,"ஞோ", rep_charret2("§»¡"));
		unicodeni = replace(unicodeni,"ஞொ", rep_charret2("¦»¡"));
		unicodeni = replace(unicodeni,"ஞா", rep_charret2("»¡"));
		unicodeni = replace(unicodeni,"ஞி", rep_charret2("»¢"));
		unicodeni = replace(unicodeni,"ஞீ", rep_charret2("»£"));
		unicodeni = replace(unicodeni,"ஞு", rep_charret2(""));
		unicodeni = replace(unicodeni,"ஞூ", rep_charret2(""));
		unicodeni = replace(unicodeni,"ஞெ", rep_charret2("¦»"));
		unicodeni = replace(unicodeni,"ஞே", rep_charret2("§»"));
		unicodeni = replace(unicodeni,"ஞை", rep_charret2("¨»"));
		unicodeni = replace(unicodeni,"ஞ்", rep_charret2("ï"));
		unicodeni = replace(unicodeni,"ஞ", rep_charret2("»"));



		unicodeni = replace(unicodeni,"டௌ", rep_charret2("¦¼ª"));
		unicodeni = replace(unicodeni,"டோ", rep_charret2("§¼¡"));
		unicodeni = replace(unicodeni,"டோ", rep_charret2("§¼¡"));

		unicodeni = replace(unicodeni,"டொ", rep_charret2("¦¼¡"));
		unicodeni = replace(unicodeni,"டொ", rep_charret2("¦¼¡"));
		unicodeni = replace(unicodeni,"டா", rep_charret2("¼¡"));
		unicodeni = replace(unicodeni,"டி", rep_charret2("Ê"));
		unicodeni = replace(unicodeni,"டீ", rep_charret2("Ë"));
		unicodeni = replace(unicodeni,"டு்", rep_charret2("Î"));
		unicodeni = replace(unicodeni,"டு", rep_charret2("Î"));
		unicodeni = replace(unicodeni,"டூ", rep_charret2("Þ"));
		unicodeni = replace(unicodeni,"டெ", rep_charret2("¦¼"));
		unicodeni = replace(unicodeni,"டே", rep_charret2("§¼"));
		unicodeni = replace(unicodeni,"டை", rep_charret2("¨¼"));
		unicodeni = replace(unicodeni,"ட்", rep_charret2("ð"));
		unicodeni = replace(unicodeni,"ட", rep_charret2("¼"));


		unicodeni = replace(unicodeni,"ணௌ", rep_charret2("¦½ª"));
		unicodeni = replace(unicodeni,"ணோ", rep_charret2("§½¡"));
		unicodeni = replace(unicodeni,"ணொ", rep_charret2("¦½¡"));
		unicodeni = replace(unicodeni,"ணா", rep_charret2("½¡"));
		unicodeni = replace(unicodeni,"ணி", rep_charret2("½¢"));
		unicodeni = replace(unicodeni,"ணீ", rep_charret2("½£"));
		unicodeni = replace(unicodeni,"ணு", rep_charret2("Ï"));
		unicodeni = replace(unicodeni,"ணூ", rep_charret2("ß"));
		unicodeni = replace(unicodeni,"ணெ", rep_charret2("¦½"));
		unicodeni = replace(unicodeni,"ணே", rep_charret2("§½"));
		unicodeni = replace(unicodeni,"ணை", rep_charret2("¨½"));
		unicodeni = replace(unicodeni,"ண்", rep_charret2("ñ"));
		unicodeni = replace(unicodeni,"ண", rep_charret2("½"));

		unicodeni = replace(unicodeni,"தௌ", rep_charret2("¦¾ª"));
		unicodeni = replace(unicodeni,"தோ", rep_charret2("§¾¡"));
		unicodeni = replace(unicodeni,"தோ", rep_charret2("§¾¡"));
		unicodeni = replace(unicodeni,"தொ", rep_charret2("¦¾¡")); //kabilan
		unicodeni = replace(unicodeni,"தொ", rep_charret2("¦¾¡"));


		unicodeni = replace(unicodeni,"தா", rep_charret2("¾¡"));
		unicodeni = replace(unicodeni,"தி்", rep_charret2("¾¢"));
		unicodeni = replace(unicodeni,"தி", rep_charret2("¾¢"));
		unicodeni = replace(unicodeni,"தீ", rep_charret2("¾£"));
		unicodeni = replace(unicodeni,"து", rep_charret2("Ð"));
		unicodeni = replace(unicodeni,"தூ", rep_charret2("à"));
		unicodeni = replace(unicodeni,"தெ", rep_charret2("¦¾"));
		unicodeni = replace(unicodeni,"தே", rep_charret2("§¾"));
		unicodeni = replace(unicodeni,"தை", rep_charret2("¨¾"));
		unicodeni = replace(unicodeni,"த‌ை", rep_charret2("¨¾"));

		unicodeni = replace(unicodeni,"த்", rep_charret2("ò"));
		unicodeni = replace(unicodeni,"த", rep_charret2("¾"));



		unicodeni = replace(unicodeni,"நௌ", rep_charret2("¦¿ª"));
		unicodeni = replace(unicodeni,"நோ", rep_charret2("§¿¡"));
		unicodeni = replace(unicodeni,"நோ", rep_charret2("§¿¡"));

		unicodeni = replace(unicodeni,"நொ", rep_charret2("¦¿¡"));
		unicodeni = replace(unicodeni,"நொ", rep_charret2("¦¿¡"));

		unicodeni = replace(unicodeni,"நா", rep_charret2("¿¡"));
		unicodeni = replace(unicodeni,"நி", rep_charret2("¿¢"));
		unicodeni = replace(unicodeni,"நீ", rep_charret2("¿£"));
		unicodeni = replace(unicodeni,"நு", rep_charret2("Ñ"));
		unicodeni = replace(unicodeni,"நூ", rep_charret2("á"));
		unicodeni = replace(unicodeni,"நெ", rep_charret2("¦¿"));
		unicodeni = replace(unicodeni,"நே", rep_charret2("§¿"));
		unicodeni = replace(unicodeni,"நை", rep_charret2("¨¿"));
		unicodeni = replace(unicodeni,"ந்", rep_charret2("ó"));
		unicodeni = replace(unicodeni,"ந", rep_charret2("¿"));


		unicodeni = replace(unicodeni,"னௌ", rep_charret2("¦Éª"));
		unicodeni = replace(unicodeni,"னோ", rep_charret2("§É¡"));
		unicodeni = replace(unicodeni,"னோ", rep_charret2("§É¡"));

		unicodeni = replace(unicodeni,"னொ", rep_charret2("¦É¡"));
		unicodeni = replace(unicodeni,"னொ", rep_charret2("¦É¡"));

		unicodeni = replace(unicodeni,"னா", rep_charret2("É¡"));
		unicodeni = replace(unicodeni,"னி", rep_charret2("É¢"));
		unicodeni = replace(unicodeni,"னீ", rep_charret2("É£"));
		unicodeni = replace(unicodeni,"னு", rep_charret2("Û"));
		unicodeni = replace(unicodeni,"னூ", rep_charret2("ë"));
		unicodeni = replace(unicodeni,"னெ", rep_charret2("¦É"));
		unicodeni = replace(unicodeni,"னே", rep_charret2("§É"));
		unicodeni = replace(unicodeni,"னை", rep_charret2("¨É"));
		unicodeni = replace(unicodeni,"ன்", rep_charret2("ý"));
		unicodeni = replace(unicodeni,"ன", rep_charret2("É"));


		unicodeni = replace(unicodeni,"பௌ", rep_charret2("¦Àª"));
		unicodeni = replace(unicodeni,"ப‌ோ", rep_charret2("§À¡"));
		unicodeni = replace(unicodeni,"போ", rep_charret2("§À¡"));
		unicodeni = replace(unicodeni,"போ", rep_charret2("§À¡"));
		unicodeni = replace(unicodeni,"பொ", rep_charret2("¦À¡"));
		unicodeni = replace(unicodeni,"பொ", rep_charret2("¦À¡"));
		unicodeni = replace(unicodeni,"ப‌ொ", rep_charret2("¦À¡"));

		unicodeni = replace(unicodeni,"பா", rep_charret2("À¡"));
		unicodeni = replace(unicodeni,"பி்", rep_charret2("À¢"));
		unicodeni = replace(unicodeni,"பி", rep_charret2("À¢"));

		unicodeni = replace(unicodeni,"பீ", rep_charret2("À£"));
		unicodeni = replace(unicodeni,"பு", rep_charret2("Ò"));
		unicodeni = replace(unicodeni,"பூ", rep_charret2("â"));
		unicodeni = replace(unicodeni,"பெ", rep_charret2("¦À"));
		unicodeni = replace(unicodeni,"பே", rep_charret2("§À"));
		unicodeni = replace(unicodeni,"பை", rep_charret2("¨À"));
		unicodeni = replace(unicodeni,"ப்",rep_charret2( "ô"));
		unicodeni = replace(unicodeni,"ப", rep_charret2("À"));


		unicodeni = replace(unicodeni,"மௌ", rep_charret2("¦Áª"));
		unicodeni = replace(unicodeni,"மோ", rep_charret2("§Á¡"));
		unicodeni = replace(unicodeni,"மோ", rep_charret2("§Á¡"));
		unicodeni = replace(unicodeni,"மொ", rep_charret2("¦Á¡"));
		unicodeni = replace(unicodeni,"மொ", rep_charret2("¦Á¡"));
		unicodeni = replace(unicodeni,"மா", rep_charret2("Á¡"));
		unicodeni = replace(unicodeni,"மி", rep_charret2("Á¢"));
		unicodeni = replace(unicodeni,"மி்", rep_charret2("Á¢"));
		//strre="Á"+strCtr+"£");
		unicodeni = replace(unicodeni,"மீ", rep_charret2("Á£"));
		unicodeni = replace(unicodeni,"மு", rep_charret2("Ó"));
		unicodeni = replace(unicodeni,"மூ", rep_charret2("ã"));
		unicodeni = replace(unicodeni,"மெ", rep_charret2("¦Á"));
		unicodeni = replace(unicodeni,"மே", rep_charret2("§Á"));
		unicodeni = replace(unicodeni,"மை", rep_charret2("¨Á"));
		unicodeni = replace(unicodeni,"ம்", rep_charret2("õ"));
		unicodeni = replace(unicodeni,"ம", rep_charret2("Á"));


		unicodeni = replace(unicodeni,"யௌ", rep_charret2("¦Âª"));
		unicodeni = replace(unicodeni,"யோ", rep_charret2("§Â¡"));
		unicodeni = replace(unicodeni,"யோ", rep_charret2("§Â¡"));
		unicodeni = replace(unicodeni,"‌யொ", rep_charret2("¦Â¡"));
		unicodeni = replace(unicodeni,"யொ", rep_charret2("¦Â¡"));
		unicodeni = replace(unicodeni,"யா", rep_charret2("Â¡"));
		unicodeni = replace(unicodeni,"யி்", rep_charret2("Â¢"));//
		unicodeni = replace(unicodeni,"யி", rep_charret2("Â¢"));
		unicodeni = replace(unicodeni,"யீ", rep_charret2("Â£"));
		unicodeni = replace(unicodeni,"யு", rep_charret2("Ô"));
		unicodeni = replace(unicodeni,"யூ", rep_charret2("ä"));
		unicodeni = replace(unicodeni,"யெ", rep_charret2("¦Â"));
		unicodeni = replace(unicodeni,"யே", rep_charret2("§Â"));
		unicodeni = replace(unicodeni,"யை", rep_charret2("¨Â"));
		unicodeni = replace(unicodeni,"ய்", rep_charret2("ö"));
		unicodeni = replace(unicodeni,"ய", rep_charret2("Â"));

		unicodeni = replace(unicodeni,"ரௌ", rep_charret2("¦Ãª"));
		unicodeni = replace(unicodeni,"ரோ", rep_charret2("§Ã¡"));
		unicodeni = replace(unicodeni,"ரோ", rep_charret2("§Ã¡"));
		unicodeni = replace(unicodeni,"ரொ", rep_charret2("¦Ã¡"));
		unicodeni = replace(unicodeni,"ரொ", rep_charret2("¦Ã¡"));
		unicodeni = replace(unicodeni,"ரா", rep_charret2("Ã¡"));
		unicodeni = replace(unicodeni,"ரி்", rep_charret2("Ã¢"));
		unicodeni = replace(unicodeni,"ரி", rep_charret2("Ã¢"));
		unicodeni = replace(unicodeni,"ரீ", rep_charret2("Ã£"));
		unicodeni = replace(unicodeni,"ரு", rep_charret2("Õ"));
		unicodeni = replace(unicodeni,"ரூ", rep_charret2("å"));
		unicodeni = replace(unicodeni,"ரெ", rep_charret2("¦Ã"));
		unicodeni = replace(unicodeni,"ரே", rep_charret2("§Ã"));
		unicodeni = replace(unicodeni,"ரை", rep_charret2("¨Ã"));
		unicodeni = replace(unicodeni,"ர‌ை", rep_charret2("¨Ã"));
		unicodeni = replace(unicodeni,"ர்", rep_charret2("÷"));
		unicodeni = replace(unicodeni,"ர", rep_charret2("Ã"));


		unicodeni = replace(unicodeni,"லௌ", rep_charret2("¦Äª"));
		unicodeni = replace(unicodeni,"லோ", rep_charret2("§Ä¡"));
		unicodeni = replace(unicodeni,"லோ", rep_charret2("§Ä¡"));
		unicodeni = replace(unicodeni,"லொ", rep_charret2("¦Ä¡"));
		unicodeni = replace(unicodeni,"லொ", rep_charret2("¦Ä¡"));
		unicodeni = replace(unicodeni,"லா", rep_charret2("Ä¡"));
		unicodeni = replace(unicodeni,"லி்", rep_charret2("Ä¢"));//
		unicodeni = replace(unicodeni,"லி", rep_charret2("Ä¢"));
		unicodeni = replace(unicodeni,"லீ", rep_charret2("Ä£"));
		unicodeni = replace(unicodeni,"லு", rep_charret2("Ö"));
		unicodeni = replace(unicodeni,"லூ", rep_charret2("æ"));
		unicodeni = replace(unicodeni,"லெ", rep_charret2("¦Ä"));
		unicodeni = replace(unicodeni,"லே", rep_charret2("§Ä"));
		unicodeni = replace(unicodeni,"லை", rep_charret2("¨Ä"));
		unicodeni = replace(unicodeni,"ல்", rep_charret2("ø"));
		unicodeni = replace(unicodeni,"ல", rep_charret2("Ä"));


		unicodeni = replace(unicodeni,"ளௌ", rep_charret2("¦Çª"));
		unicodeni = replace(unicodeni,"ளோ", rep_charret2("§Ç¡"));
		unicodeni = replace(unicodeni,"ளோ", rep_charret2("§Ç¡"));
		unicodeni = replace(unicodeni,"ளொ", rep_charret2("¦Ç¡"));
		unicodeni = replace(unicodeni,"ளா", rep_charret2("Ç¡"));
		unicodeni = replace(unicodeni,"ளி்", rep_charret2("Ç¢"));
		unicodeni = replace(unicodeni,"ளி", rep_charret2("Ç¢"));
		unicodeni = replace(unicodeni,"ளீ", rep_charret2("Ç£"));
		unicodeni = replace(unicodeni,"ளு", rep_charret2("Ù"));
		unicodeni = replace(unicodeni,"ளூ", rep_charret2("é"));
		unicodeni = replace(unicodeni,"ளெ", rep_charret2("¦Ç"));
		unicodeni = replace(unicodeni,"ளே", rep_charret2("§Ç"));
		unicodeni = replace(unicodeni,"ளை", rep_charret2("¨Ç"));
		unicodeni = replace(unicodeni,"ள‌ை", rep_charret2("¨Ç"));

		unicodeni = replace(unicodeni,"ள்", rep_charret2("û"));
		unicodeni = replace(unicodeni,"ள", rep_charret2("Ç"));

		unicodeni = replace(unicodeni,"வௌ", rep_charret2("¦Åª"));
		unicodeni = replace(unicodeni,"வோ", rep_charret2("§Å¡"));
		unicodeni = replace(unicodeni,"வோ", rep_charret2("§Å¡"));
		unicodeni = replace(unicodeni,"வொ", rep_charret2("¦Å¡"));
		unicodeni = replace(unicodeni,"வொ", rep_charret2("¦Å¡"));
		unicodeni = replace(unicodeni,"வா", rep_charret2("Å¡"));
		unicodeni = replace(unicodeni,"வி", rep_charret2("Å¢"));
		unicodeni = replace(unicodeni,"வீ", rep_charret2("Å£"));
		unicodeni = replace(unicodeni,"வு", rep_charret2("×"));
		unicodeni = replace(unicodeni,"வூ", rep_charret2("ç"));
		unicodeni = replace(unicodeni,"வெ", rep_charret2("¦Å"));
		unicodeni = replace(unicodeni,"வே", rep_charret2("§Å"));
		unicodeni = replace(unicodeni,"வை", rep_charret2("¨Å"));
		unicodeni = replace(unicodeni,"வ்", rep_charret2("ù"));
		unicodeni = replace(unicodeni,"வ", rep_charret2("Å"));


		unicodeni = replace(unicodeni,"ழௌ", rep_charret2("¦Æª"));
		unicodeni = replace(unicodeni,"ழோ", rep_charret2("§Æ¡"));
		unicodeni = replace(unicodeni,"ழொ", rep_charret2("¦Æ¡"));
		unicodeni = replace(unicodeni,"ழா", rep_charret2("Æ¡"));
		unicodeni = replace(unicodeni,"ழி", rep_charret2("Æ¢"));
		unicodeni = replace(unicodeni,"ழீ", rep_charret2("Æ£"));
		unicodeni = replace(unicodeni,"ழு", rep_charret2("Ø"));
		unicodeni = replace(unicodeni,"ழூ", rep_charret2("è"));
		unicodeni = replace(unicodeni,"ழெ", rep_charret2("¦Æ"));
		unicodeni = replace(unicodeni,"ழே", rep_charret2("§Æ"));
		unicodeni = replace(unicodeni,"ழை", rep_charret2("¨Æ"));
		unicodeni = replace(unicodeni,"ழ‌ை", rep_charret2("¨Æ"));
		unicodeni = replace(unicodeni,"ழ்", rep_charret2("ú"));
		unicodeni = replace(unicodeni,"ழ", rep_charret2("Æ"));

		unicodeni = replace(unicodeni,"றௌ", rep_charret2("¦Èª"));
		unicodeni = replace(unicodeni,"றோ", rep_charret2("§È¡"));
		unicodeni = replace(unicodeni,"றொ", rep_charret2("¦È¡"));
		unicodeni = replace(unicodeni,"றொ", rep_charret2("¦È¡"));
		unicodeni = replace(unicodeni,"றா", rep_charret2("È¡"));
		unicodeni = replace(unicodeni,"றி", rep_charret2("È¢"));
		unicodeni = replace(unicodeni,"றீ", rep_charret2("È£"));
		unicodeni = replace(unicodeni,"று", rep_charret2("Ú"));
		unicodeni = replace(unicodeni,"றூ", rep_charret2("ê"));
		unicodeni = replace(unicodeni,"றெ", rep_charret2("¦È"));
		unicodeni = replace(unicodeni,"றே", rep_charret2("§È"));
		unicodeni = replace(unicodeni,"றை", rep_charret2("¨È"));
		unicodeni = replace(unicodeni,"ற்", rep_charret2("ü"));
		unicodeni = replace(unicodeni,"ற", rep_charret2("È"));

		unicodeni = replace(unicodeni,"ஹௌ", rep_charret2("¦†ª"));
		unicodeni = replace(unicodeni,"ஹோ", rep_charret2("§†¡"));
		unicodeni = replace(unicodeni,"ஹோ", rep_charret2("§†¡"));
		unicodeni = replace(unicodeni,"ஹொ", rep_charret2("¦†¡"));
		unicodeni = replace(unicodeni,"ஹா", rep_charret2("†¡"));
		unicodeni = replace(unicodeni,"ஹி", rep_charret2("†¢"));
		unicodeni = replace(unicodeni,"ஹீ", rep_charret2("†£"));
		unicodeni = replace(unicodeni,"ஹு", rep_charret2("†¤"));
		unicodeni = replace(unicodeni,"ஹூ", rep_charret2("†¥"));
		unicodeni = replace(unicodeni,"ஹெ", rep_charret2("¦†"));
		unicodeni = replace(unicodeni,"ஹே", rep_charret2("§†"));
		//unicodeni = replace(unicodeni,"ஹை", rep_charret2("¨");
		unicodeni = replace(unicodeni,"ஹை", rep_charret2("¨†"));
		unicodeni = replace(unicodeni,"ஹ்", rep_charret2("‹ "));
		//RSK unicodeni = replace(unicodeni,"ஹ", "†");
		unicodeni = replace(unicodeni,"ஹ", rep_charret2("†"));



		unicodeni = replace(unicodeni,"ஷௌ", rep_charret2("¦„ª"));
		unicodeni = replace(unicodeni,"ஷோ", rep_charret2("§„¡"));
		unicodeni = replace(unicodeni,"ஷோ", rep_charret2("§„¡"));

		unicodeni = replace(unicodeni,"ஷொ", rep_charret2("¦„¡"));
		unicodeni = replace(unicodeni,"ஷா", rep_charret2("„¡"));
		unicodeni = replace(unicodeni,"ஷி", rep_charret2("„¢"));
		unicodeni = replace(unicodeni,"ஷீ", rep_charret2("„£"));
		unicodeni = replace(unicodeni,"ஷு", rep_charret2("„¤"));
		unicodeni = replace(unicodeni,"ஷூ", rep_charret2("„¥"));
		unicodeni = replace(unicodeni,"ஷெ", rep_charret2("¦„"));
		unicodeni = replace(unicodeni,"ஷே", rep_charret2("§„"));
		unicodeni = replace(unicodeni,"ஷை", rep_charret2("¨„"));
		unicodeni = replace(unicodeni,"ஷ்", rep_charret2("‰"));
		unicodeni = replace(unicodeni,"ஷ", rep_charret2("„"));



		unicodeni = replace(unicodeni,"ஸௌ", rep_charret2("¦…ª"));
		unicodeni = replace(unicodeni,"ஸோ", rep_charret2("§…¡"));
		unicodeni = replace(unicodeni,"ஸொ", rep_charret2("¦…¡"));
		unicodeni = replace(unicodeni,"ஸா", rep_charret2("…¡"));
		unicodeni = replace(unicodeni,"ஸி", rep_charret2("…¢"));
		unicodeni = replace(unicodeni,"ஸீ", rep_charret2("…£"));
		unicodeni = replace(unicodeni,"ஸு", rep_charret2("…¤"));
		unicodeni = replace(unicodeni,"ஸூ", rep_charret2("…¥"));
		unicodeni = replace(unicodeni,"ஸெ", rep_charret2("¦…"));
		unicodeni = replace(unicodeni,"ஸே", rep_charret2("§…"));
		unicodeni = replace(unicodeni,"ஸை", rep_charret2("¨…"));
		unicodeni = replace(unicodeni,"ஸ்", rep_charret2("Š"));
		unicodeni = replace(unicodeni,"ஸ", rep_charret2("…"));


		unicodeni = replace(unicodeni,"அ", rep_charret2("«"));
		unicodeni = replace(unicodeni,"ஆ", rep_charret2("¬"));
		unicodeni = replace(unicodeni,"இ", rep_charret2("þ"));
		unicodeni = replace(unicodeni,"ஈ", rep_charret2("®"));
		unicodeni = replace(unicodeni,"உ", rep_charret2("¯"));
		unicodeni = replace(unicodeni,"ஊ", rep_charret2("°"));
		unicodeni = replace(unicodeni,"எ", rep_charret2("±"));
		unicodeni = replace(unicodeni,"ஏ", rep_charret2("²"));
		unicodeni = replace(unicodeni,"ஐ", rep_charret2("³"));
		unicodeni = replace(unicodeni,"ஒ", rep_charret2("´"));
		unicodeni = replace(unicodeni,"ஓ", rep_charret2("µ"));
		unicodeni = replace(unicodeni,"ஒள", rep_charret2("¶"));
		unicodeni = replace(unicodeni,"ஔ", rep_charret2("¶"));

		unicodeni = replace(unicodeni,"ஃ", rep_charret2("·"));
		unicodeni = replace(unicodeni,"ஸ்ரீ",rep_charret2( ""));
		unicodeni = replace(unicodeni,"‘", rep_charret2("‘"));

		unicodeni = replace(unicodeni,"௧", rep_charret2(""));
		unicodeni = replace(unicodeni,"௨", rep_charret2(""));
		unicodeni = replace(unicodeni,"௩", rep_charret2(""));
		unicodeni = replace(unicodeni,"௪", rep_charret2(""));
		unicodeni = replace(unicodeni,"௫", rep_charret2(""));
		unicodeni = replace(unicodeni,"௰", rep_charret2(""));
		unicodeni = replace(unicodeni,"௱", rep_charret2(""));
		unicodeni = replace(unicodeni,"௲", rep_charret2("Ÿ"));
		unicodeni = replace(unicodeni,"௭", rep_charret2("–"));
		unicodeni = replace(unicodeni,"௮", rep_charret2("—"));
		unicodeni = replace(unicodeni,"௯", rep_charret2("˜"));
		unicodeni = replace(unicodeni,"௲", rep_charret2("Ÿ"));
		unicodeni = replace(unicodeni,"௭", rep_charret2("–"));

		//unicodeni = replace(unicodeni,"யொ", "¡"));
		return unicodeni;
	}

	public static String replace(String FullString, String SearchString,String ReplacementString)
	{
		StringBuffer sb = new StringBuffer();
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
		return sb.toString();
	}

	public static String rep_charret2(String str)
	{
		int OSVERSION = Build.VERSION.SDK_INT;

		if(OSVERSION < Build.VERSION_CODES.KITKAT)
			return str;

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



