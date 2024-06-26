package com.battlecrow.mimipizza;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.battlecrow.mimipizza.databinding.FragmentAboutAppBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AboutAppFragment extends Fragment {
    private LinearLayout faqLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.battlecrow.mimipizza.databinding.FragmentAboutAppBinding binding = FragmentAboutAppBinding.inflate(inflater, container, false);
        faqLayout = binding.faqButtonsLayout;
        setupFaqButtons();
        return binding.getRoot();
    }

    private void setupFaqButtons() {
        List<String> faqTexts = Arrays.asList(
                "Как сделать заказ?->\tДобавьте пиццы в корзину, выбрав их в меню (можно изменять параметры пиццы). В корзине вы увидите стоимость и сможете подтвердить заказ (оплата производится при получении товара).",
                "Какие способы оплаты доступны?->\tНа данный момент доступна только оплата при получении :(",
                "Как я могу кастомизировать пиццу?->\tВы можете добавлять дополнительные ингредиенты, а также изменять размер пиццы и толщину её теста.",
                "Работает ли история заказов?->\tНа данный момент это функция недоступна, но мы постараемся выпустить её в скором времени. Следите за обновлениями.",
                "Как долго будет готовиться моя пицца?->\tВремя - величина относительная, но мы Вас уверяем, долго ждать не придётся!",
                "Как отследить статус моего заказа?->\tОтследить статус Вашего заказа можно в разделе с корзиной после подтверждения заказа.",
                "Как изменить или отменить мой заказ?->\tК сожалению, изменение заказа невозможно. А отменить Вы его можете в любой момент с подтверждения заказа до это получения в разделе 'Корзина'.",
                "Какие акции и скидки у вас есть?->\tУ нас реализована система промокодов. Раз уж Вы сюда зашли, то дарим вам промокод pomogator на 100 рублей!",
                "Как связаться с поддержкой клиентов в случае проблем?->\tНаписать в поддержку Вы можете в разделе 'Помощь' в подразделе 'Чат'. Там Вы можете задавать все интересующие Вас вопросы.",
                "Какие временные рамки доставки у вас?->\tМы не устанавливаем временных рамок для курьеров, но просим их быть пошустрее ради наших замечательных клиентов.",
                "Какие дополнительные услуги предлагаете?->\tИз дополнительных услуг у нас только удаление аккаунта :))"
        );

        for (String faqText : faqTexts) {
            Button button = (Button) getLayoutInflater().inflate(R.layout.faq_button, faqLayout, false);
            button.setText(getFaqTitle(faqText));
            button.setOnClickListener(v -> showFaqDialog(faqText));
            faqLayout.addView(button);
        }
    }

    private String getFaqTitle(String faqText) {
        return faqText.split("->")[0];
    }

    private void showFaqDialog(String faqText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_faq, null);
        builder.setView(dialogView);

        TextView textViewTitle = dialogView.findViewById(R.id.textViewTitle);
        TextView textViewContent = dialogView.findViewById(R.id.textViewContent);
        TextView textViewExit = dialogView.findViewById(R.id.textViewExit);

        textViewTitle.setText(getFaqTitle(faqText));
        textViewContent.setText(getFaqContent(faqText));

        AlertDialog dialog = builder.create();

        textViewExit.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private String getFaqContent(String faqText) {
        return faqText.split("->")[1].trim();
    }
}