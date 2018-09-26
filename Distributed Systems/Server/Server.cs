using ParserLibrary;
using System;
using System.Collections.Generic;
using System.IO;
using System.Numerics;
using System.Text;

namespace Server
{
    public class Server
    {
        static Queue<string> F2 = new Queue<string>();
        static Queue<string> F3 = new Queue<string>();
        static Dictionary<BigInteger, byte[]> keyValuePairs = new Dictionary<BigInteger, byte[]>();
        ServerSocket ss;

        public Server()
        {
            ss = new ServerSocket();
        }

        public void Listen()
        {
            ss.Receive();
        }

        public void Persist()
        {
            while (true)
            {
                try
                {
                    if (F2.Count > 0)
                    {
                        string message = F2.Dequeue();
                        FileHelper.WriteFile($"{Directory.GetCurrentDirectory()}\\bkp.txt", message);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Unexpected exception : {0}", e.ToString());
                }
            }
        }

        public void Restore()
        {
            try
            {
                if (!File.Exists($"{Directory.GetCurrentDirectory()}\\bkp.txt"))
                {
                    using (var streamWriter = new StreamWriter($"{Directory.GetCurrentDirectory()}\\bkp.txt", true))
                    {
                        streamWriter.WriteLine("");
                    }
                }
                string[] bkp = FileHelper.ReadFile($"{Directory.GetCurrentDirectory()}\\bkp.txt");
                if (bkp.Length > 0)
                {
                    foreach (string message in bkp)
                    {
                        F3.Enqueue(message);
                    }
                    SingleExecute();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }

        public void Replicate()
        {
            while (true)
            {
                if (ss.F1.Count > 0)
                {
                    string message = ss.F1.Dequeue();
                    if (GetCommand(message) != CommandTypes.Read)
                    {
                        F2.Enqueue(message);
                    }
                    F3.Enqueue(message);
                }
            }
        }


        void CreateRegister(string message)
        {
            if (keyValuePairs.ContainsKey(GetKey(message)))
            {
                ss.Send("\tError: Key already exists");
            }
            else
            {
                keyValuePairs.Add(GetKey(message), Encoding.ASCII.GetBytes(GetValue(message)));
                ss.Send("\tKey created successfully");

            }
        }

        void ReadRegister(string message)
        {
            if (keyValuePairs.ContainsKey(GetKey(message)))
            {
                ss.Send($"\tValue:{Encoding.ASCII.GetString(keyValuePairs[GetKey(message)])}");
            }
            else
            {
                ss.Send("\tError: Key not found");
            }
        }

        void UpdateRegister(string message)
        {
            if (keyValuePairs.ContainsKey(GetKey(message)))
            {
                keyValuePairs[GetKey(message)] = Encoding.ASCII.GetBytes(GetValue(message));
                ss.Send($"\tKey {GetKey(message)} updated successfully");

            }
            else
            {
                ss.Send("\tError: Key not found");
            }
        }

        void DeleteRegister(string message)
        {
            if (keyValuePairs.ContainsKey(GetKey(message)))
            {
                keyValuePairs.Remove(GetKey(message));
                ss.Send($"\tKey {GetKey(message)} deleted successfully");
            }
            else
            {
                ss.Send("\tError: Key not found");
            }
        }

        public void SingleExecute()
        {
            string message = "";
            while (F3.Count > 0)
            {
                message = F3.Dequeue();

                switch (GetCommand(message))
                {
                    case CommandTypes.Create:
                        CreateRegister(message);
                        break;

                    case CommandTypes.Read:
                        ReadRegister(message);
                        break;

                    case CommandTypes.Update:
                        UpdateRegister(message);
                        break;

                    case CommandTypes.Delete:
                        DeleteRegister(message);
                        break;
                    default:
                        break;
                }
            }
        }

        public void Execute()
        {
            while (true)
            {
                string message = "";
                while (F3.Count > 0)
                {
                    message = F3.Dequeue();

                    switch (GetCommand(message))
                    {
                        case CommandTypes.Create:
                            CreateRegister(message);
                            break;

                        case CommandTypes.Read:
                            ReadRegister(message);
                            break;

                        case CommandTypes.Update:
                            UpdateRegister(message);
                            break;

                        case CommandTypes.Delete:
                            DeleteRegister(message);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        string GetCommand(string message)
        {
            return message.Split(" ")[0];
        }

        BigInteger GetKey(string message)
        {
            return BigInteger.Parse(message.Split(" ")[1]);
        }

        string GetValue(string message)
        {
            return message.Split(" ")[2];
        }
    }
}
