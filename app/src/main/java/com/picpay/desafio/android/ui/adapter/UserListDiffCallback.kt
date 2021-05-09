package com.picpay.desafio.android.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.model.data.User

class UserListDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    //Decide se dois objetos estão representando os mesmos itens ou não.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    //Retorna o tamanho da lista antiga.
    override fun getOldListSize(): Int {
        return oldList.size
    }

    //Retorna o tamanho da nova lista.
    override fun getNewListSize(): Int {
        return newList.size
    }

    // decide se dois itens têm os mesmos dados ou não. Este método é chamado por DiffUtil apenas se areItemsTheSame () retornar verdadeiro.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}