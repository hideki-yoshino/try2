package jp.co.sample.controller;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sample.java.Child;
import jp.co.sample.java.Grandson;
import jp.co.sample.java.MySafeImpl;
import jp.co.sample.java.Parent;

@Controller
@RequestMapping(value = "/java")
public class JavaController {
	private static final String TITLE_JABA_SELECTION = "JAVA基礎確認結果画面";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	/**
	 * Java機能の確認<br>
	 * <p>
	 * ① extends ② 3項演算子 ③ final ④ static ⑤ 配列 ⑥ 文字列結合(+ or StringBuilder) ⑦
	 * equalsメソッド ⑧ ArrayList ⑨ ⑩
	 *
	 * </p>
	 *
	 * @return view名（Java確認画面）
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	public String study(Model model) {

		model.addAttribute("title", TITLE_JABA_SELECTION);

		// ***************************************************
		/*
		 * Javaの継承の構文が持つ働きには 親クラスからフィールドやメソッドの定義を文字通り継承する（コードの再利用）
		 * 子クラスが親クラスの型を持っているが、実行時の振る舞いは代入されているインスタンスで決まる（ポリモーフィズム）
		 */
		final Child child = new Child();
		System.out.println("↓️ Java確認① ↓");
		System.out.println(child.fieldA); // field A
		System.out.println(child.fieldB); // field B
		System.out.println(child.methodA()); // method A in Child
		System.out.println(child.methodB()); // method B

		Parent parent = new Child();
		System.out.println("↓️ Java確認② ↓");
		System.out.println(parent.fieldA); // field A
		System.out.println(parent.methodA()); // method A in Child
		/*
		 * 宣言されている変数の型ではなく、実際に変数に代入されている インスタンスの型によって実行時の振る舞いが決まる
		 */
		someMethod(parent);

		parent = new Grandson();
		System.out.println("↓️ Java確認③ ↓");
		System.out.println(parent.fieldA); // field A
		System.out.println(parent.methodA()); // method A in Grandson
		someMethod(parent);

		// ***************************************************
		// ② 3項演算子
		final BigDecimal decimal = BigDecimal.valueOf(10);
		final BigDecimal decimal2 = orZero(decimal);
		System.out.println("BigDecimal1=" + decimal2);
		final BigDecimal decimal3 = orZero(null);
		System.out.println("BigDecimal2=" + decimal3);
		// ***************************************************

		final StringBuffer buff = new StringBuffer();
		final String hoge1 = "aiueo";
		final String gabo1 = "";

		for (int i = 0; i < 100; i++) {
			// gabo1 += hoge1;
			buff.append(gabo1);
			buff.append(hoge1);
		}

		// ***************************************************
		// ⑦equalsメソッド
		// リテラルが先で比較する変数を引数にすること
		final String str = null;
		if ("文字".equals(str)) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
		// NullPointerExceptionが発生
		// if (str.equals("文字")) {
		// System.out.println("true");
		// } else {
		// System.out.println("false");
		// }
		// ***************************************************

		// ***************************************************
		// ⑧ ArrayList
		// ArrayList を使う際、List型で宣言しすべきと一般的に言われています。
		// 「これは List インターフェースにある機能だけで充分で、ArrayList 等の独自の機能は必要としていない」ということを明示できる
		// Listインターフェイスをimplementsしたクラスのインスタンスならなんでもセットできる。
		// List<String> arrayList = new ArrayList<>();

		// ***************************************************

		// ***************************************************
		// Template Methodパターン
		final MySafeImpl safe = new MySafeImpl();
		if (safe.open(2, 2, 12, 1)) {
			System.out.println("success!!!");
		} else {
			System.out.println("fail!!!");
		}
		String para = "a";
		System.out.println("@NotNull=" + capitalize(para));
		para = null;
		System.out.println("@NotNull=" + capitalize(para));

		return "../view/java/resultView";

	}

	public void someMethod(Parent parent) {
		System.out.println("↓️ Java確認④ ↓");
		System.out.println(parent.methodA());
	}

	public BigDecimal orZero(BigDecimal decimal) {
		return decimal == null ? BigDecimal.ZERO : decimal;
	}

	String capitalize(@NotNull String in) {
		return in.toUpperCase(); // no null check required
	}
}