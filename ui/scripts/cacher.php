<?php
    class DatabaseCacher
    {
		private $server = "iprojsrv.cs.washington.edu";
		private $user = "wikiwrite";
		private $pass = "WikipediaMaps123";
		private $db = "wikimapsDB_test";
		private $imageTable = "ArticleImages";
		private $previewTable = "ArticleSummary";

		private $debug = false;

		public function insertImageURL($article, $data) {
			$this->insertRow($this->imageTable, $article, $data, "");
		}
		
		public function insertPreviewText($article, $data) {
			$this->insertRow($this->previewTable, $article, $data, "FALSE");
		}
		
		// Insert this row into 
		private function insertRow($table, $article, $data, $redirect)
		{
			$this->openSQL();
			
			if ($redirect == "")
				mysql_query("INSERT IGNORE INTO " . $table . " VALUES ('".mysql_real_escape_string($article)."', '".mysql_real_escape_string($data)."')")
				or die(mysql_error());
			else 
				mysql_query("INSERT IGNORE INTO " . $table . " VALUES ('".mysql_real_escape_string($article)."', '".mysql_real_escape_string($data)."', ".$redirect.")")
				or die(mysql_error());
		}
		
		/**
         * Simply opens a mySQL connection to our database
         */
		private function openSQL()
		{
			mysql_connect($this->server, $this->user, $this->pass) or die(mysql_error());
			mysql_select_db($this->db) or die(mysql_error());
		}

        /**
         * Closes our mySQL connection. Not currently used, since it closes the connection at the end of SQL script anyway
         */
		private function closeSQL()
		{
			mysql_close();
		}
		
    }

?>