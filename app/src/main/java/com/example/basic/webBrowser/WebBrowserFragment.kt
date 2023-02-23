package com.example.basic.webBrowser

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.basic.*
import com.example.basic.databinding.FragmentWebBrowserBinding

class WebBrowserFragment : Fragment() {

    private var _binding: FragmentWebBrowserBinding?=null
    private val binding get()=_binding!!

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBrowserBinding.inflate(inflater, container, false)
        
        binding.webView.apply {

            settings.javaScriptEnabled=true
            webViewClient=object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {  // 해당 페이지의 url 표시
                    binding.urlEt.setText(url)
                }
            }
        }

        binding.webView.loadUrl("https://www.google.com")

        binding.urlEt.setOnEditorActionListener { _, id, _ ->  // 글자가 입력될 때마다 호출됨
            if(id==EditorInfo.IME_ACTION_SEARCH){  // 검색 버튼이 눌렸는 지 여부
                binding.webView.loadUrl(binding.urlEt.text.toString())
                true
            }else{
                false
            }
        }

        val menuHost:MenuHost=requireActivity()  // onCreateOptionMenu

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.web_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.action_google, R.id.action_home ->{
                        binding.webView.loadUrl("https://www.google.com")
                        return true
                    }
                    R.id.action_naver ->{
                        binding.webView.loadUrl("https://www.naver.com")
                        return true
                    }
                    R.id.action_daum ->{
                        binding.webView.loadUrl("https://www.daum.net")
                        return true
                    }
                    R.id.action_call ->{
                        val intent=Intent(Intent.ACTION_DIAL)
                        intent.data= Uri.parse("tel:010-4730-9608")
                        if (intent.resolveActivity(requireActivity().packageManager)!=null){
                            startActivity(intent)
                        }
                        return true
                    }
                    R.id.action_send_text ->{
                        binding.webView.url?.let {
                            requireActivity().sendSMS("010-4730-9608","web test")
                        }
                        return true
                    }
                    R.id.action_email -> {
                        binding.webView.url?.let{
                            requireActivity().email("psb8909@naver.com","web test","잘왔냐")
                        }
                        return true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        registerForContextMenu(binding.webView) // 컨텍스트 메뉴 등록

        return binding.root
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.web_context_menu,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share ->{
                binding.webView.url?.let {
                    requireActivity().share(it)
                }
                return true
            }
            R.id.action_browser ->{
                binding.webView.url?.let {
                    requireActivity().browse(it)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}