package com.example.myapp.fragmentsNavigation

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.Gravity.START
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapp.MusicRecylerView.MyAdapter3
import com.example.myapp.MusicRecylerView.Songs
import com.example.myapp.MusicService
import com.example.myapp.R
import com.example.myapp.databinding.FolderFeaturedArtistLayoutBinding
import com.example.myapp.databinding.FolderLayoutNormalBinding
import com.example.myapp.databinding.FragmentOnlineMusicBinding
import com.example.myapp.databinding.ListTrendingLayoutBinding
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class OnlineMusicFragment : Fragment()  {

    //view binding
    private var _binding: FragmentOnlineMusicBinding? = null

    var storage: FirebaseStorage? = null
    var mRefernce: StorageReference? = null

    //trending Punjabi Arraylist
    var trendingPunjabiArraylist: ArrayList<Songs>? = null
    var trendingEnglishArraylist: ArrayList<Songs>? = null
    var trendingHindiArraylist: ArrayList<Songs>? = null

    var navController:NavController?=null
    var navigationView:NavigationView?=null
    var drawerLayout: DrawerLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_online_music, container, false)
        _binding = FragmentOnlineMusicBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //getting storage INstance and refernce from storage..
        storage = FirebaseStorage.getInstance()
        mRefernce = storage?.getReference()

        //set color of status bar..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity?.window?.statusBarColor = resources.getColor(R.color.Mint_green, null)
        } else {
            activity?.window?.statusBarColor = resources.getColor(R.color.Mint_green)

        }

        navigationView=activity?.findViewById(R.id.navdrawer)
        navigationView?.setBackgroundColor(Color.WHITE)
       // onNavigationItemClickListened()
      //  drawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawer)

        //getting arraylist of local(device) songs from Music Service..

        //getting nav controller to navigate to other fragments..
        navController = Navigation.findNavController(view)

        var arrlist: ArrayList<Songs>? = MusicService.songsList


        _binding?.includeTrendingEnglish?.rootLayoutTrending?.setOnClickListener {
            var bundle =Bundle()
           bundle.putString("Playlist","Trending_Playlist")
           bundle.putString("Folder","English")
           navController?.navigate(R.id.action_onlineMusicFragment_to_songsFragment,bundle)
       }
        _binding?.includeTrendingPunjabi?.rootLayoutTrending?.setOnClickListener {
            var bundle =Bundle()
            bundle.putString("Playlist","Trending_Playlist")
            bundle.putString("Folder","Punjabi")
            navController?.navigate(R.id.action_onlineMusicFragment_to_songsFragment,bundle)
        }
        _binding?.includeTrendingHindi?.rootLayoutTrending?.setOnClickListener {
            var bundle =Bundle()
            bundle.putString("Playlist","Trending_Playlist")
            bundle.putString("Folder","Hindi")
            navController?.navigate(R.id.action_onlineMusicFragment_to_songsFragment,bundle)
        }


        //coroutine scope to launch suspend function in it
        CoroutineScope(Dispatchers.IO).launch {

            //these fubnctions to set data in Trending Layouts
            set_Arraylist_ToTrendingLayouts(mRefernce!!, "Trending_Playlist", "English",_binding?.includeTrendingEnglish!!)
            set_Arraylist_ToTrendingLayouts(mRefernce!!, "Trending_Playlist", "Punjabi", _binding?.includeTrendingPunjabi!!)
            set_Arraylist_ToTrendingLayouts(mRefernce!!, "Trending_Playlist", "Hindi", _binding?.includeTrendingHindi!!)

            //these fubnctions to set data in Trending Layouts
             setImageToFeaturedArtists("Featured_Artists", "Ammy Virk","ammyvirkimage.jpg","Ammy Virk",_binding?.includeAmmyVirk!!,view)
            setImageToFeaturedArtists("Featured_Artists", "Amrit Maan","amrit_maan-6.jpg","Amrit Maan",_binding?.includeAmritMaan!!,view)
            setImageToFeaturedArtists("Featured_Artists", "Annie Marie","annie marie.jpg","Annie Marie",_binding?.includeAnnieMarie!!,view)
            setImageToFeaturedArtists("Featured_Artists", "Ap Dhillon","AP-dhillon-wallpapers-7.jpg","Ap Dhillon",_binding?.includeApdhillon!!,view)
            setImageToFeaturedArtists("Featured_Artists", "Arijit_Singh","arijit singh.jpg","Arijit Singh",_binding?.includeArijitSingh!!,view)
            setImageToFeaturedArtists("Featured_Artists","Camila Cabillo","camilla cabillo.jpg","Camila Cabillo",_binding?.includeCamilacabillo!!,view)
            setImageToFeaturedArtists("Featured_Artists","Diljit Dosanjh","Diljit-Dosanjh13.jpg","Diljit Dosanjh",_binding?.includeDiljitDosanjh!!,view)
            setImageToFeaturedArtists("Featured_Artists","Dilpreet Dhillon","dilpreet dhilon.jpg","Dilpreet Dhillon",_binding?.includeDilpreetdhillon!!,view)
            setImageToFeaturedArtists("Featured_Artists","Dua Lipa","dua lipa.jpg","Dua Lipa",_binding?.includeDuaLipa!!,view)
            setImageToFeaturedArtists("Featured_Artists","Gurnaam Bhullar","gurnaam bhullar.jpg","Gurnaam Bhullar",_binding?.includeGurnaamBhullar!!,view)
            setImageToFeaturedArtists("Featured_Artists","Jason Derulo","jason derulo.jpg","Jason Derulo",_binding?.includeJasonDerulo!!,view)
            setImageToFeaturedArtists("Featured_Artists","Justin Beiber","justin beiber.jpg","Justin Beiber",_binding?.includeJustinBeiber!!,view)
            setImageToFeaturedArtists("Featured_Artists","Kishore Kumar","kishore_Kumar.jpg","Kishore Kumar",_binding?.includeKishoreKumar!!,view)
            setImageToFeaturedArtists("Featured_Artists","Kygo","kygo.jpg","Kygo",_binding?.includeKygo!!,view)
            setImageToFeaturedArtists("Featured_Artists","Maninder Buttar","maninder.jpg","Maninder Buttar",_binding?.includeManinderButtar!!,view)
            setImageToFeaturedArtists("Featured_Artists","Neha_Kakkar","neha kakkr.jpg","Neha Kakkar",_binding?.includeNehaKakkar!!,view)
            setImageToFeaturedArtists("Featured_Artists","Post_Malone","post malone.jpg","Post Malone",_binding?.includePostMalone!!,view)
            setImageToFeaturedArtists("Featured_Artists","Shivjot","shivjot.jpg","Shivjot",_binding?.includeShivjot!!,view)
            setImageToFeaturedArtists("Featured_Artists","The_Weekend","the weekend.jpg","The Weeknd",_binding?.includeTheWeeknd!!,view)
            setImageToFeaturedArtists("Featured_Artists","Tones and I","tones and i.jpg","Tones and I",_binding?.includeTonesAndI!!,view)
            Log.d("YOTHREAD", Thread.currentThread().toString())
       }

        Log.d("YOTHREAD", Thread.currentThread().toString())
            setDataToRecentPlayedList(arrlist!!, view)
            setDataToNewReleasePlayedList(arrlist!!, view)

       //setting Top Charts Folders(include layouts)
        setUp_TopChartsFolder(view)
        //for setting Top Charts Folders(include layouts)

        // CtentResolver resolver=getContext().getContentResolver();
        setUp_DiscoverFolders(view)
        setUp_MoodFolders(view)

    }  //onViewClosed


    //function to set data to Recent Played
    /*suspend fun*/ fun setDataToRecentPlayedList(
        arraylist: ArrayList<Songs>,
        view: View
    )/*= withContext(Dispatchers.IO)*/ {
        val myAdapter3: MyAdapter3 = MyAdapter3(context, arraylist)

        // withContext(Dispatchers.Main){
        val recycelrView = view.findViewById<RecyclerView>(R.id.Recently_RecylerView)
        recycelrView.layoutManager =
            GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        recycelrView.adapter = myAdapter3
        // }
    }

    //function to set data to Trending include layouts
    /*suspend*/ fun setDataToNewReleasePlayedList(
        arraylist: ArrayList<Songs>,
        view: View
    )/*= withContext(Dispatchers.IO)*/ {
        val myAdapter3: MyAdapter3 = MyAdapter3(context, arraylist)
        // withContext(Dispatchers.Main){
        val recycelrView = view.findViewById<RecyclerView>(R.id.newRelease_RecylerView)
        recycelrView.layoutManager =
            GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        recycelrView.adapter = myAdapter3
        // }
    }


   /* //function to setUp Trending folders(include layouts)
    fun setUp_TrendingFolder(arrlist: ArrayList<Songs>) {


        if (arrlist != null) {
            _binding?.includeTrendingEnglish?.InLanguageTextView?.setText("In English (15)")
            _binding?.includeTrendingEnglish?.songName1?.setText(arrlist.get(0).songName)
            _binding?.includeTrendingEnglish?.songName2?.setText(arrlist.get(1).songName)
            _binding?.includeTrendingEnglish?.songName3?.setText(arrlist.get(2).songName)
            _binding?.includeTrendingEnglish?.songName4?.setText(arrlist.get(3).songName)
            _binding?.includeTrendingEnglish?.songName5?.setText(arrlist.get(4).songName)
        }
        if (arrlist != null) {
            _binding?.includeTrendingHindi?.InLanguageTextView?.setText("In Hindi (15)")
            _binding?.includeTrendingHindi?.songName1?.setText(arrlist.get(0).songName)
            _binding?.includeTrendingHindi?.songName2?.setText(arrlist.get(1).songName)
            _binding?.includeTrendingHindi?.songName3?.setText(arrlist.get(2).songName)
            _binding?.includeTrendingHindi?.songName4?.setText(arrlist.get(3).songName)
            _binding?.includeTrendingHindi?.songName5?.setText(arrlist.get(4).songName)
        }
        if (arrlist != null) {
            _binding?.includeTrendingPunjabi?.InLanguageTextView?.setText("In Punjabi (15)")
            _binding?.includeTrendingPunjabi?.songName1?.setText(arrlist.get(0).songName)
            _binding?.includeTrendingPunjabi?.songName2?.setText(arrlist.get(1).songName)
            _binding?.includeTrendingPunjabi?.songName3?.setText(arrlist.get(2).songName)
            _binding?.includeTrendingPunjabi?.songName4?.setText(arrlist.get(3).songName)
            _binding?.includeTrendingPunjabi?.songName5?.setText(arrlist.get(4).songName)
        }
    }*/

    //function to setUp Top Charts folders(include layouts)
    fun setUp_TopChartsFolder(view: View) {
        _binding?.includeEnglishTop10?.languageTextviewFolder?.setText("English")
        _binding?.includePunjabiTop10?.languageTextviewFolder?.setText("Punjabi")
        _binding?.includeHindiTop10?.languageTextviewFolder?.setText("Hindi")
        _binding?.includeUSTop10?.languageTextviewFolder?.setText("US")
        _binding?.includeDanceTop10?.languageTextviewFolder?.setText("Dance")
        _binding?.includeHindi90sTop10?.languageTextviewFolder?.setText("Hindi 90s")


        foldersOnClickedListened(_binding?.includeDanceTop10!!,"Top_Charts","Dance_Top10")
        foldersOnClickedListened(_binding?.includeHindi90sTop10!!,"Top_Charts","Hindi_90s")
        foldersOnClickedListened(_binding?.includeHindiTop10!!,"Top_Charts","Hindi_Top10")
        foldersOnClickedListened(_binding?.includeEnglishTop10!!,"Top_Charts","International_Top10")
        foldersOnClickedListened(_binding?.includePunjabiTop10!!,"Top_Charts","Punjabi_Top10")
        foldersOnClickedListened(_binding?.includeUSTop10!!,"Top_Charts","US_Top10")

    }
          //navigate to song list ftragment on click top charts folders
        fun  foldersOnClickedListened(folderLayoutNormalBinding: FolderLayoutNormalBinding,playlist:String,folder:String)
        {
            folderLayoutNormalBinding?.folderLayout.setOnClickListener{
                var bundle =Bundle()
                bundle.putString("Playlist",playlist)
                bundle.putString("Folder",folder)
                navController?.navigate(R.id.action_onlineMusicFragment_to_songsFragment,bundle)
            }

        }
    fun  foldersArtistOnClickedListened(folderLayoutNormalBinding: FolderFeaturedArtistLayoutBinding,playlist:String,folder:String)
    {
        folderLayoutNormalBinding?.folderLayout.setOnClickListener{
            var bundle =Bundle()
            bundle.putString("Playlist",playlist)
            bundle.putString("Folder",folder)
            navController?.navigate(R.id.action_onlineMusicFragment_to_songsFragment,bundle)
        }

    }






    //function to setUp Top Charts folders(include layouts)
    fun setUp_DiscoverFolders(view: View) {
        _binding?.includeRetro?.languageTextviewFolder?.setText("RETRO")
        _binding?.includeIndie?.languageTextviewFolder?.setText("INDIE")
        _binding?.includeEDM?.languageTextviewFolder?.setText("EDM")
        _binding?.includeWedding?.languageTextviewFolder?.setText("WEDDING")
        _binding?.includeDance?.languageTextviewFolder?.setText("DANCE")
        _binding?.includeRock?.languageTextviewFolder?.setText("ROCK")
        _binding?.includeKids?.languageTextviewFolder?.setText("KIDS")
        _binding?.includeGhazals?.languageTextviewFolder?.setText("GHAZAL")
    }

    //function to setUp Moods&Collection folders(include layouts)
    fun setUp_MoodFolders(view: View) {
        _binding?.includeRomance?.languageTextviewFolder?.setText("Romance")
        _binding?.include90sAndEarly?.languageTextviewFolder?.setText("90s & Early 2000s")
        _binding?.includeParty?.languageTextviewFolder?.setText("Party")
        _binding?.includeHipHop?.languageTextviewFolder?.setText("Hip-Hop")
        _binding?.includeBhakti?.languageTextviewFolder?.setText("Bhakti")
        _binding?.includeMusic?.languageTextviewFolder?.setText("Music")
        _binding?.includeHeals?.languageTextviewFolder?.setText("Heals")
        _binding?.includePunjabiHits?.languageTextviewFolder?.setText("Punjabi Top Hits")
        _binding?.includeWorkout?.languageTextviewFolder?.setText("Workout")

        foldersOnClickedListened(_binding?.includeRomance!!,"Moods&Collection","Romance")
        foldersOnClickedListened(_binding?.include90sAndEarly!!,"Moods&Collection","90s&Early2000s")
        foldersOnClickedListened(_binding?.includeParty!!,"Moods&Collection","Party")
        foldersOnClickedListened(_binding?.includeHipHop!!,"Moods&Collection","Hip-Hop")
        foldersOnClickedListened(_binding?.includeBhakti!!,"Moods&Collection","Bhakti")
        foldersOnClickedListened(_binding?.includeMusic!!,"Moods&Collection","Music")
        foldersOnClickedListened(_binding?.includeHeals!!,"Moods&Collection","Heals")
        foldersOnClickedListened(_binding?.includePunjabiHits!!,"Moods&Collection","Workout")
        foldersOnClickedListened(_binding?.includeWorkout!!,"Moods&Collection","Workout")



    }

    //this method is for offline data for artist folders
   /* fun setUp_FeaturedArtists(view: View) {
        _binding?.includeTheWeeknd?.singerName?.setText("The Weekend")
        _binding?.includeDiljitDosanjh?.singerName?.setText("Diljit Dosanjh")
        _binding?.includeJustinBeiber?.singerName?.setText("The Justin Beiber")
        _binding?.includeAmmyVirk?.singerName?.setText("Ammy virk")
        _binding?.includeKygo?.singerName?.setText("Kygo")
        _binding?.includeApdhillon?.singerName?.setText("Ap Dhillon")
        _binding?.includeCamilacabillo?.singerName?.setText("Camila Cabillo")
        _binding?.includeDilpreetdhillon?.singerName?.setText("Dilpreet Dhillon")
        _binding?.includeJasonDerulo?.singerName?.setText("Jason Derulo")
        _binding?.includeShivjot?.singerName?.setText("Shivjot")
        _binding?.includeTonesAndI?.singerName?.setText("Tones and I")
        _binding?.includeGurnaamBhullar?.singerName?.setText("Gurnaam Bhullar")
        _binding?.includeDuaLipa?.singerName?.setText("Dua lipa")
        _binding?.includeManinderButtar?.singerName?.setText("Maninder buttar")
        _binding?.includeAnnieMarie?.singerName?.setText("Annie Marie")
        _binding?.includeAmritMaan?.singerName?.setText("Amrit Maan")
        _binding?.includePostMalone?.singerName?.setText("Post Malone")
        _binding?.includeNehaKakkar?.singerName?.setText("Neha kakkar")
        _binding?.includeKishoreKumar?.singerName?.setText("Kishore kumar")
        _binding?.includeArijitSingh?.singerName?.setText("Arijit Singh")

        //functions to upload images on artist images..
        val featured_Artist = "Featured Artists"
        set_ImageToFeaturedArtist(featured_Artist, "The Weeknd", "the weekend.jpg", _binding?.includeTheWeeknd!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Ammy virk", "ammyvirkimage.jpg", _binding?.includeAmmyVirk!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Diljit Dosanjh", "Diljit-Dosanjh13.jpg", _binding?.includeDiljitDosanjh!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Amrit Maan", "amrit_maan-6.jpg", _binding?.includeAmritMaan!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Annie Marie", "annie marie.jpg", _binding?.includeAnnieMarie!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Ap dhillon", "AP-dhillon-wallpapers-7.jpg", _binding?.includeApdhillon!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Arijit singh", "arijit singh.jpg", _binding?.includeArijitSingh!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Camila Cabillo", "camilla cabillo.jpg", _binding?.includeCamilacabillo!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Dilpreet Dhillon", "dilpreet dhilon.jpg", _binding?.includeDilpreetdhillon!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Dua lipa", "dua lipa.jpg", _binding?.includeDuaLipa!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Gurnaam Bhullar", "gurnaam bhullar.jpg", _binding?.includeGurnaamBhullar!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Jason Derulo", "jason derulo.jpg", _binding?.includeJasonDerulo!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Justin Beiber", "justin beiber.jpg", _binding?.includeJustinBeiber!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Kishore kumar", "kishorekumar.jpg", _binding?.includeKishoreKumar!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Kygo", "kygo.jpg", _binding?.includeKygo!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Maninder buttar", "maninder.jpg", _binding?.includeManinderButtar!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Neha kakkar", "neha kakkr.jpg", _binding?.includeNehaKakkar!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Post malone", "post malone.jpg", _binding?.includePostMalone!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Shivjot", "shivjot.jpg", _binding?.includeShivjot!!, view)
        set_ImageToFeaturedArtist(featured_Artist, "Tones and I", "tones and i.jpg", _binding?.includeTonesAndI!!, view)


    }*/


    suspend fun set_Arraylist_ToTrendingLayouts(refernce: StorageReference, playlistName: String, folderName: String, view: ListTrendingLayoutBinding) = withContext(Dispatchers.Default)
    {

        activity?.runOnUiThread {
            view.InLanguageTextView.setText("In "+folderName +"(15)")

        }
        //getting five five  songs for every tredning playlist
        refernce?.child(playlistName)?.child(folderName)?.listAll()?.addOnSuccessListener {

            var list = it.items
            for (i in 0..list.size - 1) {
                list.get(i).metadata.addOnSuccessListener {
                   // Log.d("NAMEFIREEE",it.getCustomMetadata("SongName")!!)
                    var songName=it.getCustomMetadata("SongName")!!
                    var artist:String=it.getCustomMetadata("Artist")!!
                    var bitmapstr:String=it.getCustomMetadata("Bitmap")!!

                    val byteArray=Base64.decode(bitmapstr,Base64.DEFAULT)
                    val finalBitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)



                   /* var byteArray=Base64.decode(bitmapstr,Base64.DEFAULT)
                    var finalbitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)*/


                    when(i)
                    {
                        0 -> {view.songName1.setText(songName)
                              view.artistName1.setText(artist)

                        view.songImage1.setImageBitmap(finalBitmap)
                        }
                        1 -> {
                            view.songName2.setText(songName)
                            view.artistName2.setText(artist)
                        view.songImage2.setImageBitmap( finalBitmap)
                        }
                        2 -> {
                            view.songName3.setText(songName)
                            view.artistName3.setText(artist)
                       view.songImage3.setImageBitmap( finalBitmap  )
                        }
                        3 -> {
                            view.songName4.setText(songName)
                            view.artistName4.setText(artist)
                           view.songImage4.setImageBitmap(finalBitmap)
                        }
                        4 -> {
                            view.songName5.setText(songName)
                            view.artistName5.setText(artist)
                            view.songImage5.setImageBitmap(finalBitmap)
                        }
                    }
                }
                    }
                }//for loop closed
             }


    //this method is for online data for artist folders but this is for
    fun set_ImageToFeaturedArtist(playlistName: String, artistName: String, imageName: String, layout: FolderFeaturedArtistLayoutBinding, view: View) {
        mRefernce?.child(playlistName)?.child(artistName)
            ?.child(imageName)?.downloadUrl?.addOnSuccessListener {
                Glide.with(view).asBitmap().load(it).into(layout.singerImage)
                Log.d("FUNDD", "fnction run secuucfdfully")
            }
    }
    suspend fun setImageToFeaturedArtists(playlist:String, folder:String, imageName:String, artistName:String, artistlayout:FolderFeaturedArtistLayoutBinding, view:View)=
        withContext(Dispatchers.Main)
        {
            mRefernce?.child(playlist)?.child(folder)?.child(imageName)?.downloadUrl?.addOnSuccessListener {

                context?.let { it1 -> Glide.with(it1).asBitmap().load(it).into(artistlayout.singerImage) }
               // }
                artistlayout.singerName.setText(artistName)
                foldersArtistOnClickedListened(artistlayout,playlist,folder)
            }

        }

    /* fun  onNavigationItemClickListened()
            {
                navigationView?.setNavigationItemSelectedListener {
                    return@setNavigationItemSelectedListener when(it.itemId)
                    {
               R.id.offlineSong -> {
                  navController?.navigate(R.id.action_onlineMusicFragment_to_OfflineMusicFragment)
                  // drawerLayout?.closeDrawer(Gravity.CENTER)

                     true
                }
                R.id.uploadSongs ->
                {
                    Toast.makeText(context, "uploadSongs Songs", Toast.LENGTH_SHORT).show()
                   true
                }
                R.id.download ->
                {
                    Toast.makeText(context, "download Songs", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.notification ->
                {
                    Toast.makeText(context, "notification Songs", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Login ->
                {
                    Toast.makeText(context, "Login Songs", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Signup ->
                {
                    Toast.makeText(context, "Signup Songs", Toast.LENGTH_SHORT).show()
                     true
                }
                else -> {
                     false
                }


            }
        }
    }*/


} //class finished here




